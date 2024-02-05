package com.sowhat.data.remote

import com.sowhat.network.model.ResponseBody
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query

interface CommonApi {
    @PATCH("/stories/remove")
    suspend fun deleteFeed(
        @Header("Authorization") accessToken: String,
        @Query("story-id") feedId: Long
    ): ResponseBody<Unit?>
}