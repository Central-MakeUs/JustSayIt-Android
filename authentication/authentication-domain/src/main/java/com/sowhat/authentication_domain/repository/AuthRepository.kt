package com.sowhat.authentication_domain.repository

import com.sowhat.authentication_domain.model.NewMember
import com.sowhat.authentication_domain.model.SignIn
import com.sowhat.common.model.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {
    suspend fun autoSignIn(accessToken: String?): Resource<Boolean?>

    suspend fun signIn(platformToken: String?): Resource<SignIn>

    suspend fun signUp(
        joinInfo: RequestBody,
        profileImage: MultipartBody.Part?
    ): Resource<NewMember>
}