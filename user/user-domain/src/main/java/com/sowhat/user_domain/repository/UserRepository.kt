package com.sowhat.user_domain.repository

import com.sowhat.common.wrapper.Resource
import com.sowhat.network.model.ResponseBody
import com.sowhat.user_domain.model.UserInfoDomain
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserRepository {

    suspend fun getUserInfo(
        accessToken: String?,
        memberId: Long
    ): Resource<ResponseBody<UserInfoDomain>>

    suspend fun editUserInfo(
        accessToken: String?,
        memberId: Long,
        loginInfo: RequestBody,
        profileImage: MultipartBody.Part?
    ): Resource<ResponseBody<Unit?>>

    suspend fun withdrawUser(
        accessToken: String?,
        memberId: Long
    ): Resource<ResponseBody<Unit?>>
}