package com.practice.post_data.remote

import com.sowhat.network.model.ResponseBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PostApi {
    @Multipart
    @POST("/stories/new/{member-id}")
    suspend fun submitPost(
        @Header("Authorization") accessToken: String,
        @Path("member-id") memberId: Long,
        @Part("storyInfo") storyInfo: RequestBody,
        @Part storyImg: List<MultipartBody.Part?>?
    ): ResponseBody<Unit?>
}