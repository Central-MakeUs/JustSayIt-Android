package com.sowhat.user_data.remote

import com.sowhat.network.model.ResponseBody
import com.sowhat.user_data.model.remote.UserInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApi {

    @GET("/members/profile/me/{member-id}")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String?,
        @Path("member-id") memberId: Long,
    ): ResponseBody<UserInfo>

    @Multipart
    @PATCH("/members/profile/me/{member-id}")
    suspend fun editUserInfo(
        @Header("Authorization") accessToken: String?,
        @Path("member-id") memberId: Long,
        @Part("updateProfile") profile: RequestBody,
        @Part("profileImg") profileImage: MultipartBody.Part?,
    ): ResponseBody<Unit?>

    @POST("/members/quit/{member-id}")
    suspend fun withdrawUser(
        @Header("Authorization") accessToken: String?,
    ): ResponseBody<Unit?>
}