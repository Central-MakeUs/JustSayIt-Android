package com.sowhat.authentication_domain.use_case

import com.sowhat.authentication_domain.model.NewMember
import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.common.model.Resource
import com.sowhat.datastore.AuthDataRepository
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.concurrent.CancellationException

class AutoLoginUseCase(
    private val authRepository: AuthRepository,
    private val authDataRepository: AuthDataRepository
) {
    suspend operator fun invoke(): Resource<Boolean?> {
        try {
            val accessToken = authDataRepository.authData.first().accessToken
            if (accessToken.isNullOrEmpty()) return Resource.Error(message = "로그인이 필요합니다.")

            return authRepository.autoSignIn(accessToken)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            return Resource.Error(message = e.localizedMessage ?: "예기치 못한 오류가 발생하였습니다.")
        }
    }
}