package com.sowhat.authentication_domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignIn(
    @SerialName("accessToken")
    val accessToken: String?,
    @SerialName("isJoined")
    val isJoined: Boolean,
    @SerialName("memberId")
    val memberId: Int?
)