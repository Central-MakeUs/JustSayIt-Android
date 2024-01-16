package com.sowhat.user_domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDomain(
    @SerialName("loginType")
    val loginType: String,
    @SerialName("memberId")
    val memberId: Int,
    @SerialName("personalInfo")
    val personalInfo: PersonalInfo,
    @SerialName("profileInfo")
    val profileInfo: ProfileInfo
)

@Serializable
data class ProfileInfo(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImg")
    val profileImg: String
)

@Serializable
data class PersonalInfo(
    @SerialName("birth")
    val birth: String,
    @SerialName("gender")
    val gender: String
)