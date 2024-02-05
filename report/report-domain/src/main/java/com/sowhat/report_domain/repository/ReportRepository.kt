package com.sowhat.report_domain.repository

import com.sowhat.report_domain.model.MyFeed
import com.sowhat.report_domain.model.TodayMood
import com.sowhat.common.model.Resource

interface ReportRepository {
    suspend fun getMyFeedList(
        accessToken: String,
        sortBy: String,
//        memberId: Long,
        emotionCode: String?,
        lastId: Long?,
        size: Int
    ): Resource<MyFeed>

    suspend fun getTodayMoodData(
        accessToken: String
    ): Resource<TodayMood>

    suspend fun postNewMood(
        accessToken: String,
        mood: String
    ): Resource<Unit?>
}