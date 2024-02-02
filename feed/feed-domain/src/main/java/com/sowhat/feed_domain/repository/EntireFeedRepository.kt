package com.sowhat.feed_domain.repository

import com.sowhat.common.model.Resource
import com.sowhat.feed_domain.model.EntireFeed
import retrofit2.http.Header
import retrofit2.http.Query

interface EntireFeedRepository {
    suspend fun getEntireFeedList(
        accessToken: String,
        sortBy: String,
//        memberId: Long,
        emotionCode: String?,
        lastId: Long?,
        size: Int
    ): Resource<EntireFeed>

    suspend fun reportFeed(
        accessToken: String,
        feedId: Long,
        reportCode: String
    ): Resource<Unit?>

    suspend fun blockUser(
        accessToken: String,
        blockedId: Long
    ): Resource<Unit?>
}