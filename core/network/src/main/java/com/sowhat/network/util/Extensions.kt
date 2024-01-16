package com.sowhat.network.util

import android.util.Log
import com.sowhat.network.common.BEARER
import com.sowhat.network.common.EXCEPTION_ERROR_BODY_PARSING
import com.sowhat.network.common.KEY_CODE
import okhttp3.ResponseBody
import org.json.JSONObject

fun ResponseBody.getErrorCode(): String? {
    return try {
        val errorObject = JSONObject(this.string())
        errorObject.getString(KEY_CODE)
    } catch (e: Exception) {
        Log.e(EXCEPTION_ERROR_BODY_PARSING, "$e")
        null
    }
}

fun String.toBearerToken() = "$BEARER $this"