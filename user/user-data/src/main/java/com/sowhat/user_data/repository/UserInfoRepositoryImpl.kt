package com.sowhat.user_data.repository

import com.sowhat.common.model.Resource
import com.sowhat.network.common.ResponseCode
import com.sowhat.network.util.getHttpErrorResource
import com.sowhat.network.util.getIOErrorResource
import com.sowhat.user_data.mapper.toUserInfoDomain
import com.sowhat.user_data.remote.UserApi
import com.sowhat.user_domain.model.UserInfoDomain
import com.sowhat.user_domain.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun getUserInfo(
        accessToken: String?,
//        memberId: Long
    ): Resource<UserInfoDomain> = try {
        getUserResources(
            accessToken = accessToken,
//            memberId = memberId
        )
    } catch (e: HttpException) {
        getHttpErrorResource(e)
    } catch (e: IOException) {
        getIOErrorResource(e)
    }

    override suspend fun updateUserInfo(
        accessToken: String?,
//        memberId: Long,
        editInfo: RequestBody,
        profileImage: MultipartBody.Part?
    ): Resource<Unit?> {
        return try {
            getEditResultResource(
                accessToken = accessToken,
//                memberId = memberId,
                profileImage = profileImage,
                editInfo = editInfo
            )
        } catch (e: HttpException) {
            getHttpErrorResource(e)
        } catch (e: IOException) {
            getIOErrorResource(e)
        }
    }

    override suspend fun withdrawUser(
        accessToken: String?,
//        memberId: Long
    ): Resource<Unit?> {
        return try {
            getWithdrawResultResource(
                accessToken = accessToken,
//                memberId = memberId
            )
        } catch (e: HttpException) {
            getHttpErrorResource(e)
        } catch (e: IOException) {
            getIOErrorResource(e)
        }
    }

    private suspend fun getWithdrawResultResource(
        accessToken: String?,
//        memberId: Long
    ): Resource<Unit?> {
        val withdrawResult = userApi.withdrawUser(
            accessToken = accessToken,
//            memberId = memberId,
        )

        val isSuccessful = withdrawResult.isSuccess
                && withdrawResult.code == ResponseCode.OK.code

        val resource = if (isSuccessful) {
            Resource.Success(
                code = withdrawResult.code,
                data = withdrawResult.data,
                message = withdrawResult.message
            )
        } else {
            Resource.Error(
                code = withdrawResult.code,
                message = withdrawResult.message
            )
        }

        return resource
    }

    private suspend fun getEditResultResource(
        accessToken: String?,
//        memberId: Long,
        profileImage: MultipartBody.Part?,
        editInfo: RequestBody
    ): Resource<Unit?> {
        val editUserResult = userApi.updateUserInfo(
            accessToken = accessToken,
//            memberId = memberId,
            profileImage = profileImage,
            profile = editInfo
        )

        val isSuccessful = editUserResult.isSuccess
                && editUserResult.code == ResponseCode.OK.code

        val resource = if (isSuccessful) {
            Resource.Success(
                code = editUserResult.code,
                data = editUserResult.data,
                message = editUserResult.message
            )
        } else {
            Resource.Error(
                code = editUserResult.code,
                message = editUserResult.message
            )
        }

        return resource
    }

    private suspend fun getUserResources(
        accessToken: String?,
//        memberId: Long
    ): Resource<UserInfoDomain> {
        val userInfo = userApi.getUserInfo(
            accessToken = accessToken,
//            memberId = memberId
        )

        return userInfo.data?.let {
            Resource.Success(
                data = userInfo.data!!.toUserInfoDomain(),
                code = userInfo.code,
                message = userInfo.message
            )
        } ?: Resource.Error(
            data = null,
            code = userInfo.code,
            message = "데이터를 불러오는데 실패하였습니다."
        )
    }
}