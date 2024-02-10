package com.sowhat.authentication_data.remote

import com.sowhat.authentication_data.model.response.NewMemberInfo
import com.sowhat.authentication_data.model.response.SignInResult
import com.sowhat.authentication_domain.model.Token
import com.sowhat.network.model.ResponseBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AuthApi {
    @GET("/temp")
    suspend fun autoSignIn(
        @Header("Authorization") accessToken: String?
    ): ResponseBody<Boolean>

    @POST("/members/oauth/naver")
    suspend fun signIn(
        @Body token: Token
    ): ResponseBody<SignInResult>

    @POST("/members/management/join")
    @Multipart
    suspend fun postNewMember(
        @Part("joinInfo") joinInfo: RequestBody,
        @Part profileImg: MultipartBody.Part?,
    ): ResponseBody<NewMemberInfo>
}