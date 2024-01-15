package com.sowhat.network.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

inline fun <reified T> getRequestBody(body: T): RequestBody {
    val json = Json {
        ignoreUnknownKeys = true
    }
    val jsonString = json.encodeToString(value = body)

    return jsonString.toRequestBody("application/json".toMediaTypeOrNull())
}