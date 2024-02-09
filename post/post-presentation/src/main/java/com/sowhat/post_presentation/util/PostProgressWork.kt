package com.sowhat.post_presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.sowhat.common.model.PostingEvent
import com.sowhat.common.model.Resource
import com.sowhat.common.model.UploadProgress
import com.sowhat.common.util.UploadBody
import com.sowhat.common.util.UploadCallback
import com.sowhat.post_domain.use_case.SubmitPostUseCase
import com.sowhat.post_presentation.posting.PostViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

@HiltWorker
class PostProgressWork @AssistedInject constructor (
    private val submitPostUseCase: SubmitPostUseCase,
    private val multipartConverter: MultipartConverter,
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters
) : CoroutineWorker(context, params), UploadCallback {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun onProgressUpdate(percentage: Int) {

    }

    override fun onFinish() {
        val uploadTitle = inputData.getString(POST_TEXT) ?: "게시글을 업로드하고 있습니다."
        ++UploadProgress.current
        Log.i("postscreen", "onFinish: ${UploadProgress.current} / ${UploadProgress.max}")
        showProgressNotification(UploadProgress.current, UploadProgress.max, uploadTitle)
    }

    override fun onError() {
    }

    override suspend fun doWork(): Result {
        try {
            withContext(Dispatchers.IO) {

            }
            createPostChannel()

            val uris = inputData.getStringArray(IMAGE_URIS)?.toList()?.map {
                Uri.parse(it)
            }
            val anonymous = inputData.getBoolean(ANONYMOUS, false)
            val postText = inputData.getString(POST_TEXT) ?: return Result.failure()
            val opened = inputData.getBoolean(OPENED, false)
            val emotion = inputData.getString(EMOTION) ?: return Result.failure()
            val empathyList = inputData.getStringArray(EMPATHY_LIST)?.toList() ?: return Result.failure()

            val requestBody = multipartConverter.getPostRequestBodyData(
                anonymous,
                postText,
                emotion,
                empathyList,
                opened
            ) ?: return Result.failure()
            val imageFiles = multipartConverter.convertUriIntoFile(uris)

            val requestBodyAsUploadBody = UploadBody(requestBody, "application/json", this)
            val imagesBodyAsUploadBody = imageFiles?.map {
                val progressBody = UploadBody(it, "image/*", this)
                MultipartBody.Part.createFormData(PARTNAME_IMG, it.name, progressBody)
            }

            UploadProgress.max = ((uris?.size ?: 0) + 1) * 2
            val result = submitPostUseCase(requestBodyAsUploadBody, imagesBodyAsUploadBody)

            when (result) {
                is Resource.Success -> {
//                    uiState = uiState.copy(isLoading = false)
//                    postingEventChannel.send(PostingEvent.NavigateUp)
                    Log.i("PostWork", "doWork: post success")
                    showCompleteNotification(postText)
                    return Result.success()
                }

                is Resource.Error -> {
//                    uiState = uiState.copy(isLoading = false, errorMessage = result.message)
//                    postingEventChannel.send(
//                        PostingEvent.Error(
//                            result.message
//                                ?: "예상치 못한 오류가 발생했습니다."
//                        )
//                    )
                    showFailureNotification(postText)
                    return Result.failure()
                }
            }
        } catch (e: Exception) {
            showFailureNotification("게시글을 업로드하지 못하였습니다.")
            return Result.failure()
        }
    }

    private fun showProgressNotification(progress: Int, max: Int, title: String) {
        val notification = NotificationCompat.Builder(context, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setContentTitle(title)
            .setContentText("게시글을 업로드하고 있습니다.")
            .setProgress(max, progress, false)
            .setOngoing(true)
            .build()

        notificationManager.notify(POST_NOTIFICATION_TAG, notification)
    }

    private fun showCompleteNotification(title: String) {
        val notification = NotificationCompat.Builder(context, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setContentTitle(title)
            .setContentText("게시글 업로드가 완료되었습니다.")
            .build()

        notificationManager.notify(POST_NOTIFICATION_TAG, notification)

        UploadProgress.max = 0
        UploadProgress.current = 0
    }

    private fun showFailureNotification(title: String) {
        val notification = NotificationCompat.Builder(context, POST_CHANNEL_ID)
            .setSmallIcon(com.sowhat.designsystem.R.drawable.ic_app_notification_24)
            .setColor(Color.argb(0, 0, 0, 0))
            .setContentTitle(title)
            .setContentText("게시글을 업로드하지 못하였습니다.")
//            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(POST_NOTIFICATION_TAG, notification)

        UploadProgress.max = 0
        UploadProgress.current = 0
    }

    private fun createPostChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val postChannel = NotificationChannel(
                POST_CHANNEL_ID,
                "게시 진행도 알림",
                NotificationManager.IMPORTANCE_LOW,
            )
            postChannel.description = "게시글 업로드의 진행도를 나타내기 위한 채널입니다."
            notificationManager.createNotificationChannel(postChannel)
        }
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

    }
}