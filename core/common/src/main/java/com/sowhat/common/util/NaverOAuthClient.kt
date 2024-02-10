package com.sowhat.common.util

import android.content.Context
import android.util.Log
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
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

    /**
     * 로그아웃
     * 애플리케이션에서 로그아웃할 때는 NaverIdLoginSDK.logout() 메서드 */
    fun startNaverLogout() {
        NaverIdLoginSDK.logout()
    }

    /**
     * 네이버 아이디와 애플리케이션의 연동을 해제하는 기능은 NidOAuthLogin().callDeleteTokenApi() 메서드로 구현
     * 연동을 해제하면 클라이언트에 저장된 토큰과 서버에 저장된 토큰이 모두 삭제
     */
    fun startNaverDeleteToken(){
        NidOAuthLogin().callDeleteTokenApi(object : OAuthLoginCallback {
            override fun onSuccess() {

            }
            override fun onFailure(httpStatus: Int, message: String) {
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        })
    }
}