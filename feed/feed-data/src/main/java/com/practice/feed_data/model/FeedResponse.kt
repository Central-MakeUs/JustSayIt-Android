package com.practice.feed_data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedResponse(
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("storyInfo")
    val storyInfo: List<StoryInfo>
)