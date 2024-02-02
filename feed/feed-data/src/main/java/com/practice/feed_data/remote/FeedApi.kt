package com.practice.feed_data.remote

import com.practice.feed_data.model.FeedResponse
import com.sowhat.network.model.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {
    @GET("/stories/all/{sort}")
    suspend fun getFeedData(
        @Header("Authorization") accessToken: String,
        @Path("sort") sortBy: String,
//        @Path("member-id") memberId: Long,
        @Query("emotion-code") emotionCode: String?,
        @Query("story-id") lastId: Long?,
        @Query("size") size: Int
    ): ResponseBody<FeedResponse>

    @POST("/report/stories")
    suspend fun reportFeed(
        @Header("Authorization") accessToken: String,
        @Query("story-id") feedId: Long,
        @Query("report-code") reportCode: String
    ): ResponseBody<Unit?>

    @POST("/members/block")
    suspend fun blockUser(
        @Header("Authorization") accessToken: String,
        @Query("blocked-id") blockedId: Long
    ): ResponseBody<Unit?>
}