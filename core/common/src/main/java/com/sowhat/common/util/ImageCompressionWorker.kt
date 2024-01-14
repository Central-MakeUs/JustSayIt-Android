package com.sowhat.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt

class ImageCompressionWorker(
    private val appContext: Context,
    private val params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val inputImageStringUri = params.inputData.getString(KEY_IMAGE_URI)
            val compressionKilobytes = params.inputData.getLong(
                KEY_COMPRESSION_KILOBYTES,
                0L
            )

            val inputImageUri = Uri.parse(inputImageStringUri)
            val inputImageBytes = appContext.contentResolver.openInputStream(inputImageUri)?.use {
                it.readBytes()
            } ?: return@withContext Result.failure()

            // original bitmap

            val bitmap = BitmapFactory.decodeByteArray(
                inputImageBytes,
                0,
                inputImageBytes.size
            )

            // compression
            var outputBytes: ByteArray
            var quality = 100 // compress 메소드의 quality -> 0부터 100까지. 100이 원본에 가깝고 0이 가장 낮은 화질
            do {
                val outputStream = ByteArrayOutputStream()
                outputStream.use { ost ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, ost)
                    outputBytes = ost.toByteArray()
                    quality -= (quality * 0.1).roundToInt()
                }
            } while(outputBytes.size > compressionKilobytes && quality > 40)

            val file = File(appContext.cacheDir, "${params.id}.jpg")
            file.writeBytes(outputBytes)

            Result.success(
                workDataOf(
                    KEY_RESULT_URI to file.absolutePath
                )
            )
        }
    }

    companion object {
        const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
        const val KEY_COMPRESSION_KILOBYTES = "KEY_COMPRESSION_KILOBYTES"
        const val KEY_RESULT_URI = "KEY_RESULT_URI"
    }
}