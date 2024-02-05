package com.sowhat.feed_domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockBody(
    @SerialName("blockedId")
    val blockedId: Long
)