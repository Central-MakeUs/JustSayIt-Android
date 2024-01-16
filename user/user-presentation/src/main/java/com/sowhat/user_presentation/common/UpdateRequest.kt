package com.sowhat.user_presentation.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRequest(
    @SerialName("nickname")
    val nickname: String,
)
