package com.practice.report_domain.use_case

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.practice.database.FeedDatabase
import com.practice.database.entity.MyFeedEntity
import com.practice.report_domain.FeedRemoteMediator
import com.practice.report_domain.repository.ReportRepository
import com.sowhat.datastore.AuthDataRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMyFeedUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
    private val authDataRepository: AuthDataRepository,
    private val feedDatabase: FeedDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(
        sortBy: String,
//        lastId: Long?,
//        hasNext: Boolean,
        emotion: String?
    ): Pager<Int, MyFeedEntity> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
//            initialKey = ,
            remoteMediator = FeedRemoteMediator(
                reportRepository = reportRepository,
                authDataRepository = authDataRepository,
                feedDatabase = feedDatabase,
                sortBy = sortBy,
//                lastId = lastId,
//                hasNext = hasNext,
                emotion = emotion
            ),
            pagingSourceFactory = {
                feedDatabase.myFeedDao.getAllMyFeedItems()
            }
        )
    }

    companion object {
        private const val ITEMS_PER_PAGE = 10
    }
}