package com.sowhat.user_data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfo(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImg")
    val profileImg: String
)