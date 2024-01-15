package com.sowhat.authentication_data.repository

import com.sowhat.authentication_data.mapper.toNewMember
import com.sowhat.authentication_data.mapper.toSignIn
import com.sowhat.authentication_data.remote.AuthApi
import com.sowhat.authentication_domain.model.NewMember
import com.sowhat.authentication_domain.model.SignIn
import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.common.wrapper.Resource
import com.sowhat.network.util.getHttpErrorResource
import com.sowhat.network.util.getIOErrorResource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun signIn(
        platformToken: String?
    ): Resource<SignIn> {
        return try {
            getSignInResource(platformToken)
        } catch (e: HttpException) {
            getHttpErrorResource(e)
        } catch (e: IOException) {
            getIOErrorResource(e)
        }
    }

    override suspend fun signUp(
        loginInfo: RequestBody,
        profileImage: MultipartBody.Part?
    ): Resource<NewMember> {
        return try {
            getSignUpResource(loginInfo, profileImage)
        } catch (e: HttpException) {
            getHttpErrorResource(e)
        } catch (e: IOException) {
            getIOErrorResource(e)
        }
    }

    private suspend fun getSignUpResource(
        loginInfo: RequestBody,
        profileImage: MultipartBody.Part?
    ): Resource<NewMember> {
        val newMemberDto = authApi.postNewMember(
            loginInfo = loginInfo,
            profileImg = profileImage
        )
        return newMemberDto.data?.let { signUpData ->
            Resource.Success(
                data = signUpData.toNewMember(),
                code = newMemberDto.code,
                message = newMemberDto.message
            )
        } ?: Resource.Error(
            data = null,
            code = newMemberDto.code,
            message = newMemberDto.message
        )
    }

    private suspend fun getSignInResource(platformToken: String?): Resource<SignIn> {
        val signInResult = authApi.signIn(platformToken)
        return signInResult.data?.let { signInData ->
            Resource.Success(
                data = signInData.toSignIn(),
                code = signInResult.code,
                message = signInResult.message
            )
        } ?: Resource.Error(
            data = null,
            code = signInResult.code,
            message = signInResult.message
        )
    }
}