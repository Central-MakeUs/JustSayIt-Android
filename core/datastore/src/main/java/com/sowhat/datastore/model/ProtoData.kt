package com.sowhat.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthData(
    var accessToken: String?,
    var refreshToken: String?,
    var platformToken: String?,
    var platformStatus: String?,
    var fcmToken: String?,
    var deviceNumber: String?,
    var memberId: Long?
)

// @Serializable
// data class AppSettings()