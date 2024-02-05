package com.sowhat.report_data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyFeedResponse(
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("storyInfo")
    val storyInfo: List<StoryInfo>
)