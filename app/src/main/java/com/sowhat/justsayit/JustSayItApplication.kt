package com.sowhat.justsayit

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JustSayItApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initNaverSignIn()
        initKakaoSignIn()

        val keyHash = Utility.getKeyHash(this)
        Log.i(TAG, keyHash)
    }

    private fun initNaverSignIn() {
        NaverIdLoginSDK.initialize(
            context = this,
            clientId = BuildConfig.NAVER_CLIENT_ID,
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
            clientName = resources.getString(R.string.app_name)
        )
    }

    private fun initKakaoSignIn() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }

    companion object {
        const val TAG = "JustSayItApplication"
    }
}