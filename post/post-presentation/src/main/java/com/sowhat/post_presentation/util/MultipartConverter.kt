package com.sowhat.post_presentation.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.sowhat.common.util.getImageFile
import com.sowhat.common.util.getImageMultipartBody
import com.sowhat.network.util.getRequestBody
import com.sowhat.post_presentation.common.EditFormState
import com.sowhat.post_presentation.common.EditRequest
import com.sowhat.post_presentation.common.PostFormState
import com.sowhat.post_presentation.common.PostRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.NullPointerException
import javax.inject.Inject

class MultipartConverter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun convertUriIntoMultipart(uris: List<Uri>, multipartName: String): List<MultipartBody.Part>? {
        try {
            Log.i(TAG, "convertUriIntoMultipart: uri list : $uris")
            if (uris.isEmpty()) return null

            val multipartBodyList = mutableListOf<MultipartBody.Part>()

            uris.forEach {
                val currentTime = System.currentTimeMillis().toString()
                val file = getImageMultipartBody(context, it, multipartName, "${currentTime}.jpg")
                multipartBodyList.add(file)
            }

            return multipartBodyList
        } catch (e: Exception) {
            Log.e(TAG, "이미지를 변환하지 못하였습니다. ${e.localizedMessage}")
            return null
        }
    }

    fun convertUriIntoFile(uris: List<Uri>?): List<File>? {
        try {
            if (uris == null) return null
            if (uris.isEmpty()) return null
            val fileList = mutableListOf<File>()

            uris.forEach { uri ->
                val currentTime = System.currentTimeMillis().toString()
                fileList.add(getImageFile(context, uri, "${currentTime}.jpg"))
            }
            return fileList
        } catch (e: Exception) {
            return null
        }
    }

    fun getPostRequestBodyData(formState: PostFormState): RequestBody? = try {
        val requestBody = PostRequest(
            anonymous = formState.isAnonymous,
            content = formState.postText,
            emotion = formState.currentMood?.postData ?: throw Exception("현재 감정이 없습니다."),
            emotionOfEmpathy = formState.sympathyMoodItems.map { it.postData },
            opened = formState.isOpened
        )

        val requestPart = getRequestBody(requestBody)
        Log.i(TAG, requestBody.toString())

        requestPart
    } catch (e: Exception) {
        Log.e(TAG, "requestbody를 변환하지 못하였습니다. ${e.localizedMessage}")
        null
    }

    fun getPostRequestBodyData(
        anonymous: Boolean,
        content: String,
        emotion: String,
        emotionOfEmpathy: List<String>,
        opened: Boolean
    ): RequestBody? = try {
        val requestBody = PostRequest(
            anonymous = anonymous,
            content = content,
            emotion = emotion,
            emotionOfEmpathy = emotionOfEmpathy,
            opened = opened
        )

        val requestPart = getRequestBody(requestBody)
        Log.i(TAG, requestBody.toString())

        requestPart
    } catch (e: Exception) {
        Log.e(TAG, "requestbody를 변환하지 못하였습니다. ${e.localizedMessage}")
        null
    }

    fun getEditRequestBodyData(formState: EditFormState): RequestBody? = try {
        val requestBody = EditRequest(
            anonymous = formState.isAnonymous,
            content = formState.postText,
            emotion = formState.currentMood?.postData ?: throw Exception("현재 감정이 없습니다."),
            emotionOfEmpathy = formState.sympathyMoodItems.map { it.postData },
            opened = formState.isOpened,
            removedPhoto = formState.deletedUrlId,
            storyId = formState.storyId ?: throw Exception("아이디가 없습니다.")
        )

        val requestPart = getRequestBody(requestBody)
        Log.i(TAG, requestBody.toString())

        requestPart
    } catch (e: Exception) {
        Log.e(TAG, "requestbody를 변환하지 못하였습니다. ${e.localizedMessage}")
        null
    }

    companion object {
        private const val STORY_IMG = "storyImg"
        private const val TAG = "PostScreen"
    }
}