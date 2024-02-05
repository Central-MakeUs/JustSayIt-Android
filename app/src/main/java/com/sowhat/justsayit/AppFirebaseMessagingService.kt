package com.sowhat.justsayit

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sowhat.common.model.FCMData
import com.sowhat.datastore.use_case.UpdateFcmTokenUseCase
import com.sowhat.notification.use_case.InsertNotificationDataUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AppFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var updateFcmTokenUseCase: UpdateFcmTokenUseCase
    @Inject
    lateinit var insertNotificationDataUseCase: InsertNotificationDataUseCase

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
        val time = message.sentTime
        val date = getDate(time)
        Log.d(TAG, "Message notification payload: ${message.notification}")
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${message.data}")
            scope.launch {
                insertDataToDatabase(message.data, date)
                sendNotification(dataPayload = message.data, date = date)
            }
        }
    }

    private fun sendNotification(dataPayload: Map<String, String>, date: String) {
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
        intent.putExtra("date", date)

        val notification = getNotificationBuilder(dataPayload, pendingIntent)
        notification?.let {
            notificationManager.notify(notificationId, it.build())
        }
    }

    private suspend fun insertDataToDatabase(dataPayload: Map<String, String>, date: String) {
        val fcmData = FCMData(
            title = dataPayload.getValue("title"),
            body = dataPayload.getValue("body"),
            targetCategory = dataPayload.getValue("targetCategory"),
            targetData = dataPayload.getValue("targetData"),
            date = date
        )

        insertNotificationDataUseCase(fcmData)
    }

    private fun getNotificationBuilder(
        dataPayload: Map<String, String>,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder? {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val title = dataPayload["title"].toString()
        val body = dataPayload["body"].toString()
//        val targetCategory = dataPayload["targetCategory"].toString()
//        val targetData = dataPayload["targetData"].toString()
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

    private fun getDate(timeMillis: Long): String {
        val formatter = SimpleDateFormat("yyyy. MM. dd", Locale.KOREA)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        return formatter.format(calendar.time)
    }

    companion object {
        private const val TAG = "AppFirebaseMessagingService"
        const val EVENT_CHANNEL_ID = "event_channel"
        const val EVENT_TITLE = "events"
//        const val ETC_CHANNEL_ID = "etc_channel"
//        const val  = "etc_channel"
    }
}