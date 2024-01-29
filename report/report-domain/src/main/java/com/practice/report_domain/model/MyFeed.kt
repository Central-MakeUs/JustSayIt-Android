package com.practice.report_domain.model

import com.practice.database.entity.MyFeedEntity

data class MyFeed(
    val feedItems: List<MyFeedEntity>?,
    val hasNext: Boolean
)
