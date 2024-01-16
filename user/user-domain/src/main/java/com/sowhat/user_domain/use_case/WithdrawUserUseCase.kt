package com.sowhat.user_domain.use_case

import com.sowhat.common.wrapper.Resource
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.user_domain.model.UserInfoDomain
import com.sowhat.user_domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WithdrawUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authDatastore: AuthDataRepository
) {
    suspend operator fun invoke(): Resource<Unit?> {
        val authData = authDatastore.authData.first()
        val accessToken = authData.accessToken
        val memberId = authData.memberId

        if (accessToken == null || memberId == null) {
            return Resource.Error(data = null, code = null, message = "기기에 사용자 정보가 없습니다.")
        }

        return userRepository.withdrawUser(
            accessToken = accessToken,
            memberId = memberId
        )
    }
}