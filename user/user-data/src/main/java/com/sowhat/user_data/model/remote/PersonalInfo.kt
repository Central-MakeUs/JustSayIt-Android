package com.sowhat.user_data.model.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonalInfo(
    @SerialName("birth")
    val birth: String,
    @SerialName("gender")
    val gender: String
)