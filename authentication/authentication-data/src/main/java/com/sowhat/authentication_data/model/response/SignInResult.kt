package com.sowhat.authentication_data.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResult(
    @SerialName("accessToken")
    val accessToken: String?,
    @SerialName("isJoined")
    val isJoined: Boolean,
    @SerialName("email")
    val email: String?
)