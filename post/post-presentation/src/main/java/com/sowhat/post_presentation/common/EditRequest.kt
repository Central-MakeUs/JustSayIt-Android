package com.sowhat.post_presentation.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditRequest(
    @SerialName("anonymous")
    val anonymous: Boolean,
    @SerialName("content")
    val content: String,
    @SerialName("emotion")
    val emotion: String,
    @SerialName("emotionOfEmpathy")
    val emotionOfEmpathy: List<String>,
    @SerialName("opened")
    val opened: Boolean,
    @SerialName("removedPhoto")
    val removedPhoto: List<Long>,
    @SerialName("storyId")
    val storyId: Long
)