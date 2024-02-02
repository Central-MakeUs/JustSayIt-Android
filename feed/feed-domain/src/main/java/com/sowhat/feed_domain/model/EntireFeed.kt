package com.sowhat.feed_domain.model

import com.practice.database.entity.EntireFeedEntity

data class EntireFeed(
    val feedItems: List<EntireFeedEntity>?,
    val hasNext: Boolean
)
