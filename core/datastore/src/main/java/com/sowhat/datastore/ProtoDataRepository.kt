package com.sowhat.datastore

import com.sowhat.datastore.model.AuthData
import kotlinx.coroutines.flow.Flow

interface AuthDataRepository {
    val authData: Flow<AuthData>
    suspend fun updatePlatform(platform: String)
    suspend fun updatePlatformToken(platformToken: String)
    suspend fun updateAccessToken(accessToken: String)
    suspend fun updateRefreshToken(refreshToken: String)
    suspend fun updateFcmToken(fcmToken: String)
    suspend fun updateDeviceNumber(deviceNumber: String)
    suspend fun updateMemberId(memberId: Long)
    suspend fun resetData()
}

interface AppSettingsRepository {

}