package com.sowhat.user_domain.repository

import com.sowhat.common.model.Resource
import com.sowhat.user_domain.model.UserInfoDomain
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserRepository {

    suspend fun getUserInfo(
        accessToken: String?,
//        memberId: Long
    ): Resource<UserInfoDomain>

    suspend fun updateUserInfo(
        accessToken: String?,
//        memberId: Long,
        editInfo: RequestBody,
        profileImage: MultipartBody.Part?
    ): Resource<Unit?>

    suspend fun withdrawUser(
        accessToken: String?,
//        memberId: Long
    ): Resource<Unit?>
}