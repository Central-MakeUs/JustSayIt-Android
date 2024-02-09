package com.sowhat.justsayit

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostProgressNotificationService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val intent = Intent(context, MainActivity::class.java)
    private val pendingIntent = PendingIntent.getActivity(
        context,
        (System.currentTimeMillis() / 7).toInt(),
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    fun showProgressNotification(progress: Int, max: Int, title: String) {
        val notification = NotificationCompat.Builder(context, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setContentTitle(title)
            .setContentText("게시글을 업로드하고 있습니다.")
            .setProgress(max, progress, false)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        notificationManager.notify(POST_NOTIFICATION_TAG, notification)

        if (progress == max) showCompleteNotification(title)
    }

    private fun showCompleteNotification(title: String) {
        val notification = NotificationCompat.Builder(context, POST_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText("게시글 업로드가 완료되었습니다.")
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(
            POST_NOTIFICATION_TAG, // 만약 notify할 때마다 다른 아이디를 사용한다면, 업데이트 될 때마다 다른 notification이 나타난다.
            notification
        )
    }

    companion object {
        const val POST_CHANNEL_ID = "post_channel"
        private const val POST_NOTIFICATION_TAG = 1
    }
}