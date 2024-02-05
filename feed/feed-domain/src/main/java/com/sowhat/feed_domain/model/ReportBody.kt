package com.sowhat.feed_domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportBody(
    @SerialName("reportCode")
    val reportCode: String,
    @SerialName("storyId")
    val storyId: Long
)