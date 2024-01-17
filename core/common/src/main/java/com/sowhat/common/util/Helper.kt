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
    uri: Uri,
    partName: String
): MultipartBody.Part {
    val cacheDir = appContext.cacheDir
    val file = File(cacheDir, "profile_image.jpg")
    val inputStream = appContext.contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(file)
    inputStream!!.copyTo(outputStream)

    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
    val part = MultipartBody.Part.createFormData(partName, file.name, requestBody)

    inputStream.close()

    return part
//
//    val file: File? = uri.path?.let { File(it) }
//    var inputStream: InputStream? = null
//    try {
//        inputStream = appContext.contentResolver.openInputStream(uri)
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//    val bitmap = BitmapFactory.decodeStream(inputStream)
//    val byteArrayOutputStream = ByteArrayOutputStream()
//    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
//    val requestBody = byteArrayOutputStream.toByteArray().toRequestBody("image/jpg".toMediaTypeOrNull())
//    val uploadFile = MultipartBody.Part.createFormData("postImg", "${file?.name}" ,requestBody)
//
//    return uploadFile
}

