package com.practice.report_domain.repository

import androidx.paging.Pager
import com.practice.database.entity.MyFeedEntity
import com.practice.report_domain.model.MyFeed
import com.sowhat.common.model.Resource
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ReportRepository {
    suspend fun getMyFeedList(
        accessToken: String,
        sortBy: String,
//        memberId: Long,
        emotionCode: String?,
        lastId: Long?,
        size: Int
    ): Resource<MyFeed>
}