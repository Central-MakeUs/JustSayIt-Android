package com.sowhat.authentication_domain.use_case

import com.sowhat.authentication_domain.model.SignIn
import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.common.model.Resource

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