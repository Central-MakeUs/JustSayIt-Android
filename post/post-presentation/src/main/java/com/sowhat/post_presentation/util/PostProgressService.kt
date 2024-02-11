package com.sowhat.post_presentation.util

import android.app.Notification
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.sowhat.common.model.Resource
import com.sowhat.common.model.UploadProgress
import com.sowhat.common.util.UploadBody
import com.sowhat.common.util.UploadCallback
import com.sowhat.post_domain.use_case.SubmitPostUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@AndroidEntryPoint
class PostProgressService : LifecycleService(), UploadCallback {
    @Inject
    lateinit var submitPostUseCase: SubmitPostUseCase
    @Inject
    lateinit var multipartConverter: MultipartConverter

    private var anonymous: Boolean? = null
    private lateinit var postText: String
    private var opened: Boolean? = null
    private lateinit var emotion: String
    private lateinit var empathyList: List<String>
    private lateinit var uris: List<Uri>

    private lateinit var postProgressReceiver: PostProgressReceiver

    private val notificationBuilder = NotificationCompat.Builder(this, POST_CHANNEL_ID)
        .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
        .setColor(Color.argb(0, 0, 0, 0))
        .setVibrate(null)
        .setSound(null)

    override fun onCreate() {
        super.onCreate()
        postProgressReceiver = PostProgressReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Actions.START.toString())
        intentFilter.addAction(Actions.SUCCESS.toString())
        intentFilter.addAction(Actions.ERROR.toString())
        registerReceiver(postProgressReceiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            Log.i(TAG, "onStartCommand: start command")
            anonymous = getBooleanExtra(ANONYMOUS, false)
            postText = getStringExtra(POST_TEXT) ?: return@apply
            opened = getBooleanExtra(OPENED, false)
            emotion = getStringExtra(EMOTION) ?: return@apply
            empathyList = getStringArrayExtra(EMPATHY_LIST)?.toList() ?: return@apply
            uris = getStringArrayExtra(IMAGE_URIS)?.toList()?.map {
                Uri.parse(it)
            } ?: emptyList()
        }

        startPosting()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startPosting() {

        lifecycleScope.launch {
            try {
                // startForegroundService()가 호출되고 5초 이내에 startForeground()가 수행되어야 하므로 바로 표출 시도
                showStartNotification()
                sendBroadcast(Intent(Actions.START.toString()))

                val requestBody = multipartConverter.getPostRequestBodyData(
                    anonymous ?: return@launch,
                    postText,
                    emotion,
                    empathyList,
                    opened ?: return@launch
                ) ?: return@launch
                val imageFiles = multipartConverter.convertUriIntoFile(uris)

                val requestBodyAsUploadBody = UploadBody(requestBody, "application/json", this@PostProgressService)
                val imagesBodyAsUploadBody = imageFiles?.map {
                    val progressBody = UploadBody(it, "image/*", this@PostProgressService)
                    MultipartBody.Part.createFormData(PARTNAME_IMG, it.name, progressBody)
                }

                UploadProgress.max = (uris.size + 1) * 2
                val result = submitPostUseCase(requestBodyAsUploadBody, imagesBodyAsUploadBody)

                when (result) {
                    is Resource.Success -> {
                        Log.i(TAG, "doWork: post success")
                        showCompleteNotification(postText)
                        val intent = Intent(Actions.SUCCESS.toString())
                        sendBroadcast(intent)
                    }

                    is Resource.Error -> {
                        showFailureNotification(postText)
                        val intent = Intent(Actions.ERROR.toString())
                        sendBroadcast(intent)
                    }
                }
            } catch (e: Exception) {
                showFailureNotification("게시글을 업로드하지 못하였습니다.")
                val intent = Intent(Actions.ERROR.toString())
                sendBroadcast(intent)
            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {

    }

    override fun onFinish() {
        val uploadTitle = postText
        ++UploadProgress.current
        Log.i("postscreen", "onFinish: ${UploadProgress.current} / ${UploadProgress.max}")
        showProgressNotification(UploadProgress.current, UploadProgress.max, uploadTitle)
    }

    override fun onError() {
    }

    private fun showStartNotification() {
        val notification = NotificationCompat.Builder(this, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setVibrate(null)
            .setSound(null)
            .setContentText("게시글 업로드를 시작합니다.")
            .setOngoing(true)
            .build()

        startForeground(POST_NOTIFICATION_TAG, notification)
    }

    private fun showProgressNotification(progress: Int, max: Int, title: String) {
        val notification = NotificationCompat.Builder(this, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setVibrate(null)
            .setSound(null)
            .setContentTitle(title)
            .setContentText("게시글을 업로드하고 있습니다.")
            .setProgress(max, progress, false)
            .setOngoing(true)
            .build()

        // https://stackoverflow.com/questions/17394115/multiple-call-to-startforeground
        stopForeground(STOP_FOREGROUND_DETACH)
        startForeground(POST_NOTIFICATION_TAG, notification)
    }

    private fun showCompleteNotification(title: String) {
        val notification = notificationBuilder
            .setContentTitle(title)
//            .setProgress(0, 0, false)
            .setContentText("게시글 업로드가 완료되었습니다.")
            .setOngoing(false)
            .build()

        stopForeground(STOP_FOREGROUND_DETACH)
        startForeground(POST_NOTIFICATION_TAG, notification)

        UploadProgress.max = 0
        UploadProgress.current = 0
    }

    private fun showFailureNotification(title: String) {
        val notification = notificationBuilder
            .setContentTitle(title)
//            .setProgress(0, 0, false)
            .setContentText("게시글을 업로드하지 못하였습니다.")
            .setOngoing(false)
            .build()

        stopForeground(STOP_FOREGROUND_DETACH)
        startForeground(POST_NOTIFICATION_TAG, notification)

        UploadProgress.max = 0
        UploadProgress.current = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(postProgressReceiver)
    }

    companion object {
        const val POST_CHANNEL_ID = "post_channel"
        private const val POST_NOTIFICATION_TAG = 1
        const val IMAGE_URIS = "images"
        const val ANONYMOUS = "anonymous"
        const val POST_TEXT = "post_text"
        const val OPENED = "opened"
        const val EMOTION = "emotion"
        const val EMPATHY_LIST = "empathy_list"
        const val POST_BODY = "post_body"
        const val POST_FEED = "post_feed"

        private const val PARTNAME_IMG = "storyImg"

        private const val TAG = "PostProgressService"

    }

    enum class Actions {
        START, SUCCESS, ERROR
    }
}