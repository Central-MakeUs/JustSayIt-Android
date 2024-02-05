package com.sowhat.feed_domain.repository

import com.sowhat.common.model.Resource
import com.sowhat.feed_domain.model.BlockBody
import com.sowhat.feed_domain.model.EntireFeed
import com.sowhat.feed_domain.model.PostEmpathyBody
import com.sowhat.feed_domain.model.ReportBody
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
        reportBody: ReportBody
    ): Resource<Unit?>

    suspend fun blockUser(
        accessToken: String,
        blockBody: BlockBody
    ): Resource<Unit?>


    suspend fun postFeedEmpathy(
        accessToken: String,
        postEmpathyBody: PostEmpathyBody
    ): Resource<Unit?>


    suspend fun cancelFeedEmpathy(
        accessToken: String,
        feedId: Long,
        previousEmpathy: String?
    ): Resource<Unit?>
}