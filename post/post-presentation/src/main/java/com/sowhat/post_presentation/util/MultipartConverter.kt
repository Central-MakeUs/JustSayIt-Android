package com.sowhat.post_presentation.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.sowhat.common.util.getImageMultipartBody
import com.sowhat.network.util.getRequestBody
import com.sowhat.post_presentation.common.EditFormState
import com.sowhat.post_presentation.common.EditRequest
import com.sowhat.post_presentation.common.PostFormState
import com.sowhat.post_presentation.common.PostRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.NullPointerException
import javax.inject.Inject

class MultipartConverter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun convertUriIntoMultipart(uris: List<Uri>): List<MultipartBody.Part>? {
        try {
            if (uris.isEmpty()) return null

            val multipartBodyList = mutableListOf<MultipartBody.Part>()

            uris.forEach {
                val currentTime = System.currentTimeMillis().toString()
                val file = getImageMultipartBody(context, it, STORY_IMG, "${currentTime}.jpg")
                multipartBodyList.add(file)
            }

            return multipartBodyList
        } catch (e: Exception) {
            Log.e(TAG, "이미지를 변환하지 못하였습니다. ${e.localizedMessage}")
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