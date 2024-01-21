package com.sowhat.authentication_presentation.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    @SerialName("token")
    val token: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("loginType")
    val loginType: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("birth")
    val birth: String
)