package com.sowhat.common.util

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.coroutineScope
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.ForwardingSink
import okio.Sink
import okio.buffer
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.Buffer

interface UploadCallback {
    fun onProgressUpdate(percentage: Int)
    fun onFinish()
    fun onError()
}

class UploadBody(
    private val file: File?,
    private val requestBody: RequestBody?,
    private val contentType: String,
    private val callback: UploadCallback
) : RequestBody() {

//    private var mCountingSink: CountingSink? = null

    constructor(
        requestBody: RequestBody,
        contentType: String,
        callback: UploadCallback
    ) : this(
        file = null,
        requestBody = requestBody,
        contentType = contentType,
        callback = callback
    )

    constructor(
        file: File,
        contentType: String,
        callback: UploadCallback
    ) : this(
        file = file,
        requestBody = null,
        contentType = contentType,
        callback = callback
    )

//    inner class CountingSink(delegate: Sink?) : ForwardingSink(delegate!!) {
//        private var bytesWritten: Long = 0
//        private val handler = Handler(Looper.getMainLooper())
//        @Throws(IOException::class)
//        override fun write(source: okio.Buffer, byteCount: Long) {
//            try {
//                if (bytesWritten == byteCount) {
//                    callback.onFinish()
//                    return
//                }
//                bytesWritten += byteCount
//
//                //This will invoke twice if logging intercept is enable
//                handler.post(ProgressUpdate(bytesWritten, contentLength()))
//                super.write(source, byteCount)
//                delegate.flush() // I have added this line to manually flush the sink
//            } catch (e: Exception) {
//                callback.onError()
//            }
//        }
//    }

    inner class ProgressUpdate(
        private val uploaded: Long,
        private val total: Long
    ) : Runnable {
        override fun run() {
            callback.onProgressUpdate((uploaded / total * 100).toInt())
        }
    }

    override fun contentType(): MediaType? = contentType.toMediaTypeOrNull()

    override fun contentLength(): Long = file?.length() ?: (requestBody?.contentLength() ?: 0)

    override fun writeTo(sink: BufferedSink) {
        // write all the contents or the request into the bufferedSink
        if (file != null) {
//            mCountingSink = CountingSink(sink)
//            val bufferedSink: BufferedSink = mCountingSink!!.buffer()
//            multipartBody.body.writeTo(bufferedSink)
//            bufferedSink.flush()
            val length = file.length()
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            val fileInputStream = FileInputStream(file)

            // currently uploaded bytes that are uploaded(upload status)
            var uploaded = 0L
            fileInputStream.use { fis ->
                var read: Int

                try {
                    // read all the contents from the file inputstream
                    while (fis.read(buffer).also { read = it } != -1) {
//                        handler.post(ProgressUpdate(uploaded, length))
                        uploaded += read // read : 읽힌 만큼을 더함
                        sink.write(buffer, 0, read)
                    }
                } catch (e: Exception) {
                    callback.onError()
                }

                callback.onFinish()
            }
        }

        if (requestBody != null) {
            // write all the contents or the request into the bufferedSink
            val length = requestBody.contentLength()
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

            // currently uploaded bytes that are uploaded(upload status)
            var uploaded = 0L

            try {
                requestBody.writeTo(sink)
            } catch (e: Exception) {
                callback.onError()
            }

            callback.onFinish()
        }

    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048 // 2MB
    }
}