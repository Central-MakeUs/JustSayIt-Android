package com.sowhat.justsayit

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

object NaverOAuthClient {
    private const val TAG = "NaverOAuthClient"

    private val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
            val accessToken = NaverIdLoginSDK.getAccessToken()
            Log.i(TAG, "access token : $accessToken")
        }
        override fun onFailure(httpStatus: Int, message: String) {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            Log.e(TAG,"errorCode:$errorCode, errorDesc:$errorDescription")
        }
        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

    suspend fun signIn(context: Context) {
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }
}