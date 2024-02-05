package com.sowhat.feed_domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostEmpathyBody(
    @SerialName("emotionCode")
    val emotionCode: String,
    @SerialName("storyId")
    val storyId: Long
)