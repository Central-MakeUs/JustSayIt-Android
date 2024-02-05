package com.sowhat.domain.use_case

import com.sowhat.domain.repository.CommonRepository
import com.sowhat.common.model.Resource
import com.sowhat.datastore.AuthDataRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteFeedUseCase @Inject constructor(
    private val authDataRepository: AuthDataRepository,
    private val commonRepository: CommonRepository
) {
    suspend operator fun invoke(
        feedId: Long
    ): Resource<Unit?> {
        val accessToken = authDataRepository.authData.first().accessToken
        return accessToken?.let {
            commonRepository.deleteFeed(it, feedId)
        } ?: Resource.Error(message = "로그인이 필요합니다.")
    }
}