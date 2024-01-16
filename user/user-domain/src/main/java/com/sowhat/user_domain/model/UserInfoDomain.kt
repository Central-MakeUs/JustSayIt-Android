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
    val personalInfo: PersonalInfoDomain,
    @SerialName("profileInfo")
    val profileInfo: ProfileInfoDomain
)

@Serializable
data class ProfileInfoDomain(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImg")
    val profileImg: String
)

@Serializable
data class PersonalInfoDomain(
    @SerialName("birth")
    val birth: String,
    @SerialName("gender")
    val gender: String
)