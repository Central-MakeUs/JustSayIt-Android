package com.sowhat.post_data.remote

import com.sowhat.common.util.UploadBody
import com.sowhat.network.model.ResponseBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface PostApi {
    @Multipart
    @POST("/stories/new")
    suspend fun submitPost(
        @Header("Authorization") accessToken: String,
//        @Path("member-id") memberId: Long,
        @Part("storyInfo") storyInfo: RequestBody,
        @Part storyImg: List<MultipartBody.Part?>?
    ): ResponseBody<Unit?>

    @Multipart
    @PATCH("/stories/edit")
    suspend fun editPost(
        @Header("Authorization") accessToken: String,
        @Part("storyInfo") storyInfo: RequestBody,
        @Part newImg: List<MultipartBody.Part?>?
    ): ResponseBody<Unit?>
}