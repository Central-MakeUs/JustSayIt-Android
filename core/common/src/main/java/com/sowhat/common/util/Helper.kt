package com.sowhat.common.util

import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream


fun getImageMultipartBody(
    appContext: Context,
    uri: Uri?,
    partName: String,
    fileName: String
): MultipartBody.Part {
    // 반드시 서버에서 정의해놓은 이름으로 partName을 설정해야 한다.
    if (uri == null) {
        val emptyFile = "".toRequestBody("*/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, "", emptyFile)
    }
    val cacheDir = appContext.cacheDir
    val file = File(cacheDir, fileName)
    val inputStream = appContext.contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(file)
    inputStream!!.copyTo(outputStream)

    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
    val part = MultipartBody.Part.createFormData(partName, file.name, requestBody)

    inputStream.close()

    return part

}

fun String.toDate(): String = try {
    this.split("T")
        .first()
        .split("-")
        .mapIndexed { index, date -> if (index == 0) date.slice(IntRange(2, 3)) else date }
        .joinToString(".")
} catch (e: Exception) {
    Log.e("toDate", "error : ${e.message}")
    "server error"
}