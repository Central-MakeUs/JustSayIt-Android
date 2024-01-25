package com.sowhat.datastore

import androidx.datastore.core.DataStore
import com.sowhat.datastore.model.AuthData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDataRepositoryImpl @Inject constructor(
    private val authDataStore: DataStore<AuthData>
) : AuthDataRepository {
    override val authData: Flow<AuthData>
        get() = authDataStore.data

    override suspend fun updatePlatform(platform: String) {
        authDataStore.updateData { authData ->
            authData.copy(platformStatus = platform)
        }
    }

    override suspend fun updatePlatformToken(platformToken: String) {
        authDataStore.updateData { authData ->
            authData.copy(platformToken = platformToken)
        }
    }

    override suspend fun updateAccessToken(accessToken: String) {
        authDataStore.updateData { authData ->
            authData.copy(accessToken = accessToken)
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        authDataStore.updateData { authData ->
            authData.copy(refreshToken = refreshToken)
        }
    }

    override suspend fun updateFcmToken(fcmToken: String) {
        authDataStore.updateData { authData ->
            authData.copy(fcmToken = fcmToken)
        }
    }

    override suspend fun updateDeviceNumber(deviceNumber: String) {
        authDataStore.updateData { authData ->
            authData.copy(deviceNumber = deviceNumber)
        }
    }

    override suspend fun updateMemberId(memberId: Long) {
        authDataStore.updateData { authData ->
            authData.copy(memberId = memberId)
        }
    }

    override suspend fun updateForSignOut() {
        authDataStore.updateData { authData ->
            authData.copy(
                accessToken = null,
                refreshToken = null
            )
        }
    }

    override suspend fun resetData() {
        authDataStore.updateData { authData ->
            authData.copy(
                accessToken = null,
                refreshToken = null,
                platformToken = null,
                platformStatus = null,
//                fcmToken = null,
//                deviceNumber = null
            )
        }
    }
}