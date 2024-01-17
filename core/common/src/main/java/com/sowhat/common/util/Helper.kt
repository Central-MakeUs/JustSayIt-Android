package com.sowhat.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.security.AccessController.getContext


fun getFile(
    appContext: Context,
    uri: Uri?,
    partName: String
): MultipartBody.Part {
    // 반드시 서버에서 정의해놓은 이름으로 partName을 설정해야 한다.
    if (uri == null) {
        val emptyFile = "".toRequestBody("*/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, "", emptyFile)
    }
    val cacheDir = appContext.cacheDir
    val file = File(cacheDir, "profile_image.jpg")
    val inputStream = appContext.contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(file)
    inputStream!!.copyTo(outputStream)

    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
    val part = MultipartBody.Part.createFormData(partName, file.name, requestBody)

    inputStream.close()

    return part

}

