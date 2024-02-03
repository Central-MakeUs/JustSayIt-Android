package com.sowhat.feed_domain.repository

import com.sowhat.common.model.Resource
import com.sowhat.feed_domain.model.EntireFeed
import com.sowhat.network.model.ResponseBody
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
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


    suspend fun postFeedEmpathy(
        accessToken: String,
        feedId: Long,
        emotionCode: String
    ): Resource<Unit?>


    suspend fun cancelFeedEmpathy(
        accessToken: String,
        feedId: Long,
    ): Resource<Unit?>
}