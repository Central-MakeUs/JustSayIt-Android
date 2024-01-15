package com.sowhat.authentication_domain.repository

import com.sowhat.authentication_domain.model.NewMember
import com.sowhat.authentication_domain.model.NewMemberInfo
import com.sowhat.authentication_domain.model.SignIn
import com.sowhat.common.wrapper.Resource
import com.sowhat.network.model.ResponseBody
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface AuthRepository {
    suspend fun signIn(platformToken: String?): Resource<SignIn>

    suspend fun signUp(
        loginInfo: RequestBody,
        profileImage: MultipartBody.Part?
    ): Resource<NewMember>
}