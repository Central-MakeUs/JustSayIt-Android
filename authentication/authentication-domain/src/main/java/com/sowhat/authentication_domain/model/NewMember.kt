package com.sowhat.authentication_domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewMember(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("memberId")
    val memberId: Long
)