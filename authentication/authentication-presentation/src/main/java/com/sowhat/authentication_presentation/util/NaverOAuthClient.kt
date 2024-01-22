package com.sowhat.authentication_presentation.util

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object NaverOAuthClient {
    private const val TAG = "NaverOAuthClient"

    suspend fun signIn(context: Context) = getAccessToken(context)

    private suspend fun getAccessToken(context: Context) = suspendCoroutine { continuation ->
        NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                val accessToken = NaverIdLoginSDK.getAccessToken()
                Log.i(TAG, "access token : $accessToken")
                continuation.resume(accessToken)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e(TAG,"errorCode:$errorCode, errorDesc:$errorDescription")
                continuation.resume(null)
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
                continuation.resume(null)
            }
        })
    }
}