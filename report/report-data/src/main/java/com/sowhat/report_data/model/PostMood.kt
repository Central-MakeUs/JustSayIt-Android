package com.sowhat.report_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostMood(
    @SerialName("moodCode")
    val moodCode: String
)
