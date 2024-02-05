package com.sowhat.justsayit

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JustSayItApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d(TAG, token)
        })

        initNaverSignIn()
        initKakaoSignIn()
        createNotificationChannels()

        val keyHash = Utility.getKeyHash(this)
        Log.i(TAG, keyHash)
    }

    private fun createNotificationChannels() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val eventChannel = NotificationChannel(
                AppFirebaseMessagingService.EVENT_CHANNEL_ID,
                "이벤트 알림",
                NotificationManager.IMPORTANCE_HIGH,
            )
            eventChannel.description = "그냥, 그렇다고에서 제공하는 이벤트 관련 알림 채널입니다."
            notificationManager.createNotificationChannel(eventChannel)
        }
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