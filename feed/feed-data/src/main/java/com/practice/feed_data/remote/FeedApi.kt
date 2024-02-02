package com.practice.feed_data.remote

import com.practice.feed_data.model.FeedResponse
import com.sowhat.network.model.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {
    @GET("/stories/all/{sortBy}/{member-id}")
    suspend fun getFeedData(
        @Header("Authorization") accessToken: String,
        @Path("sort") sortBy: String,
        @Path("member-id") memberId: Long,
        @Query("emotion-code") emotionCode: String?,
        @Query("story-id") lastId: Long?,
        @Query("size") size: Int
    ): ResponseBody<FeedResponse>
}