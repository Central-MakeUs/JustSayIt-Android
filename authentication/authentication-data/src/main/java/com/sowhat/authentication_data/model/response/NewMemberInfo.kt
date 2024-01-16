package com.sowhat.authentication_data.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewMemberInfo(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("memberId")
    val memberId: Long
)