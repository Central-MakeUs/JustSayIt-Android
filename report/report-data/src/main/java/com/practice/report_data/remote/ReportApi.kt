package com.practice.report_data.remote

import com.practice.report_data.model.MyFeedResponse
import com.sowhat.network.model.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ReportApi {
    @GET("/stories/me/{sort}")
    suspend fun getMyFeedList(
        @Header("Authorization") accessToken: String,
        @Path("sort") sortBy: String,
//        @Path("member-id") memberId: Long,
        @Query("emotion-code") emotionCode: String?,
        @Query("story-id") lastId: Long?,
        @Query("size") size: Int
    ): ResponseBody<MyFeedResponse>
}