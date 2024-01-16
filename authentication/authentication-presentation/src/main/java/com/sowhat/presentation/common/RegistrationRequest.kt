package com.sowhat.presentation.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    @SerialName("birth")
    val birth: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("loginType")
    val loginType: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("token")
    val token: String
)