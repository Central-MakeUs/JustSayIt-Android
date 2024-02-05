package com.sowhat.justsayit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sowhat.datastore.use_case.UpdateFcmTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var updateFcmTokenUseCase: UpdateFcmTokenUseCase

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
//        createNotificationChannels()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        scope.launch {
            updateFcmTokenUseCase(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "Message notification payload: ${message.notification}")
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${message.data}")
            sendNotification(dataPayload = message.data)
        }
    }

    private fun sendNotification(dataPayload: Map<String, String>) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val requestCode = (System.currentTimeMillis() / 7).toInt()
        val notificationId: Int = (System.currentTimeMillis() / 7).toInt()
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        for (key in dataPayload.keys) {
            val data = dataPayload.getValue(key)
            Log.i(TAG, "$key : $data")
            intent.putExtra(key, data)
        }

        val notification = getNotificationBuilder(dataPayload, pendingIntent)
        notification?.let {
            notificationManager.notify(notificationId, it.build())
        }
    }

    private fun getNotificationBuilder(
        dataPayload: Map<String, String>,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder? {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val title = dataPayload["title"].toString()
        val body = dataPayload["body"].toString()
        return when (title) {
            NotificationChannels.EVENT.title -> {
                NotificationCompat.Builder(this@AppFirebaseMessagingService, NotificationChannels.EVENT.channelId)
                    .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
                    .setColor(Color.argb(0, 0, 0, 0))
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    // 메시지 클릭 시 이동할 화면 정의한 intent
                    .setContentIntent(pendingIntent)
            }
            else -> null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        private const val TAG = "AppFirebaseMessagingService"
        const val EVENT_CHANNEL_ID = "event_channel"
        const val EVENT_TITLE = "events"
//        const val ETC_CHANNEL_ID = "etc_channel"
//        const val  = "etc_channel"
    }
}