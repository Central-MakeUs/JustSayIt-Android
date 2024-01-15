package com.sowhat.authentication_data.remote

import com.sowhat.authentication_data.model.response.NewMemberInfo
import com.sowhat.authentication_data.model.response.SignInResult
import com.sowhat.network.model.ResponseBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AuthApi {
    @GET("/members/check")
    suspend fun signIn(
        @Query("platformToken") platformToken: String
    ): ResponseBody<SignInResult>

    @POST("/members/join")
    @Multipart
    suspend fun postNewMember(
        @Part("loginInfo") loginInfo: RequestBody,
        @Part profileImg: MultipartBody.Part?,
    ): ResponseBody<NewMemberInfo>
}