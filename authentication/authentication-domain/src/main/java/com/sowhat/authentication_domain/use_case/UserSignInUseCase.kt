package com.sowhat.authentication_domain.use_case

import com.sowhat.authentication_domain.model.NewMember
import com.sowhat.authentication_domain.model.SignIn
import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.common.wrapper.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserSignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        platformToken: String?
    ): Resource<SignIn> {
        return authRepository.signIn(
            platformToken = platformToken
        )
    }
}