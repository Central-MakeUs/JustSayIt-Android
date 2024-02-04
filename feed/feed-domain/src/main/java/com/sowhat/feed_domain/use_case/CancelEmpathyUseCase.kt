package com.sowhat.feed_domain.use_case

import com.sowhat.common.model.Resource
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.feed_domain.repository.EntireFeedRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CancelEmpathyUseCase @Inject constructor(
    private val entireFeedRepository: EntireFeedRepository,
    private val authDataRepository: AuthDataRepository,
) {
    suspend operator fun invoke(
        feedId: Long,
        previousEmpathy: String?
    ): Resource<Unit?> {
        val accessToken = authDataRepository.authData.first().accessToken
        return accessToken?.let {
            entireFeedRepository.cancelFeedEmpathy(
                accessToken = accessToken,
                feedId = feedId,
                previousEmpathy = previousEmpathy
            )
        } ?: Resource.Error(
            data = null,
            code = null,
            message = "로그인이 이뤄지지 않았습니다."
        )
    }
}