package com.sowhat.user_data.mapper

import com.sowhat.user_data.model.remote.PersonalInfo
import com.sowhat.user_data.model.remote.ProfileInfo
import com.sowhat.user_data.model.remote.UserInfo
import com.sowhat.user_domain.model.PersonalInfoDomain
import com.sowhat.user_domain.model.ProfileInfoDomain
import com.sowhat.user_domain.model.UserInfoDomain

fun UserInfo.toUserInfoDomain(): UserInfoDomain = UserInfoDomain(
    loginType = provider,
    memberId = memberId,
    personalInfo = personalInfo.toPersonalInfoDomain(),
    profileInfo = profileInfo.toProfileInfoDomain()
)

fun PersonalInfo.toPersonalInfoDomain(): PersonalInfoDomain = PersonalInfoDomain(
    birth = birth,
    gender = gender
)

fun ProfileInfo.toProfileInfoDomain(): ProfileInfoDomain = ProfileInfoDomain(
    nickname = nickname,
    profileImg = profileImg
)