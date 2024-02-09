package com.sowhat.post_presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleOwner
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
import kotlinx.coroutines.async
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

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

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
                Log.i(TAG, "startPosting")

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

                UploadProgress.max = ((uris.size ?: 0) + 1) * 2
                val result = submitPostUseCase(requestBodyAsUploadBody, imagesBodyAsUploadBody)

                when (result) {
                    is Resource.Success -> {
                        Log.i(TAG, "doWork: post success")
                        showCompleteNotification(postText)
                    }

                    is Resource.Error -> {
                        showFailureNotification(postText)
                    }
                }
            } catch (e: Exception) {
                showFailureNotification("게시글을 업로드하지 못하였습니다.")
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

    private fun showProgressNotification(progress: Int, max: Int, title: String) {
        val notification = NotificationCompat.Builder(this, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setContentTitle(title)
            .setContentText("게시글을 업로드하고 있습니다.")
            .setProgress(max, progress, false)
            .setOngoing(true)
            .build()

        startForeground(POST_NOTIFICATION_TAG, notification)
    }

    private fun showCompleteNotification(title: String) {
        val notification = NotificationCompat.Builder(this, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setContentTitle(title)
            .setContentText("게시글 업로드가 완료되었습니다.")
            .build()

        startForeground(POST_NOTIFICATION_TAG, notification)

        UploadProgress.max = 0
        UploadProgress.current = 0
    }

    private fun showFailureNotification(title: String) {
        val notification = NotificationCompat.Builder(this, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setContentTitle(title)
            .setContentText("게시글을 업로드하지 못하였습니다.")
//            .setContentIntent(pendingIntent)
            .build()

        startForeground(POST_NOTIFICATION_TAG, notification)

        UploadProgress.max = 0
        UploadProgress.current = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
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
        START, STOP
    }
}