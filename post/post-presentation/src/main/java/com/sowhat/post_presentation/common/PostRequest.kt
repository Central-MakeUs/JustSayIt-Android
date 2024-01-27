package com.sowhat.post_presentation.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    @SerialName("anonymous")
    val anonymous: Boolean,
    @SerialName("content")
    val content: String,
    @SerialName("emotion")
    val emotion: String,
    // TODO 서버에 새로 올라가면 emotionOfEmpathy로 변경
    @SerialName("emotionOfEmpathy")
    val emotionOfEmpathy: List<String>,
    @SerialName("opened")
    val opened: Boolean
)