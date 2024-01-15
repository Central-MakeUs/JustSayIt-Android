package com.sowhat.authentication_domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NewMemberInfo(
    val token: String,
    val nickname: String,
    val loginType: String,
    val gender: String,
    val birth: String
)
