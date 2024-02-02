package com.sowhat.feed_domain.repository

import com.sowhat.common.model.Resource
import com.sowhat.feed_domain.model.EntireFeed

interface EntireFeedRepository {
    suspend fun getEntireFeedList(
        accessToken: String,
        sortBy: String,
        memberId: Long,
        emotionCode: String?,
        lastId: Long?,
        size: Int
    ): Resource<EntireFeed>
}