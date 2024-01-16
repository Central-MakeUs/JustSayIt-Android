package com.sowhat.user_data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("loginType")
    val loginType: String,
    @SerialName("memberId")
    val memberId: Int,
    @SerialName("personalInfo")
    val personalInfo: PersonalInfo,
    @SerialName("profileInfo")
    val profileInfo: ProfileInfo
)