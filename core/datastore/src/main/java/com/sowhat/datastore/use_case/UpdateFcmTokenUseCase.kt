package com.sowhat.datastore.use_case

import com.sowhat.datastore.AuthDataRepository
import javax.inject.Inject

class UpdateFcmTokenUseCase @Inject constructor(
    private val authDataRepository: AuthDataRepository
) {
    suspend operator fun invoke(fcmToken: String) {
        authDataRepository.updateFcmToken(fcmToken)
    }
}