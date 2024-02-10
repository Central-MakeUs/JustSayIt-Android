package com.sowhat.authentication_presentation.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    @SerialName("email")
    val email: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("provider")
    val provider: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("birth")
    val birth: String
)