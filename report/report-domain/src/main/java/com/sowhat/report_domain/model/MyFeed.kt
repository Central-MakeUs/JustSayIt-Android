package com.sowhat.report_domain.model

import com.sowhat.database.entity.MyFeedEntity

data class MyFeed(
    val feedItems: List<MyFeedEntity>?,
    val hasNext: Boolean
)
