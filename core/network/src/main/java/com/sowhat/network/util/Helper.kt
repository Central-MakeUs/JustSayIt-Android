package com.sowhat.network.util

import com.sowhat.common.wrapper.Resource
import com.sowhat.network.common.EXCEPTION_UNEXPECTED_ERROR
import com.sowhat.network.common.ResponseCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

inline fun <reified T> getRequestBody(body: T): RequestBody {
    val json = Json {
        ignoreUnknownKeys = true
    }
    // import kotlinx.serialization.encodeToString 직접 추가하여 오류 방지
    val jsonString = json.encodeToString(value = body)

    return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
}

fun <T> getIOErrorResource(e: IOException): Resource.Error<T> =
    Resource.Error(
        message = e.localizedMessage ?: EXCEPTION_UNEXPECTED_ERROR
    )

fun <T> getHttpErrorResource(e: HttpException): Resource.Error<T> {
    val errorBody = e.response()?.errorBody()
    val errorCode = errorBody?.getErrorCode()
    return errorCode?.let { code ->
        Resource.Error(message = ResponseCode.getErrorMessageByCode(code))
    } ?: Resource.Error(message = e.localizedMessage ?: EXCEPTION_UNEXPECTED_ERROR)
}