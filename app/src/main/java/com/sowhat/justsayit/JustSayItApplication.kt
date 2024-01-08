package com.sowhat.justsayit

import android.app.Application
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JustSayItApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NaverIdLoginSDK.initialize(
            context = this,
            clientId = BuildConfig.NAVER_CLIENT_ID,
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
            clientName = resources.getString(R.string.app_name)
        )
    }
}