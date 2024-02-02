package com.sowhat.feed_domain.use_case

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.practice.database.FeedDatabase
import com.practice.database.entity.EntireFeedEntity
import com.practice.database.entity.MyFeedEntity
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.feed_domain.EntireFeedRemoteMediator
import com.sowhat.feed_domain.repository.EntireFeedRepository
import javax.inject.Inject

class GetEntireFeedUseCase @Inject constructor(
    private val entireFeedRepository: EntireFeedRepository,
    private val authDataRepository: AuthDataRepository,
    private val feedDatabase: FeedDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(
        sortBy: String,
        emotion: String?
    ): Pager<Int, EntireFeedEntity> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = EntireFeedRemoteMediator(
                entireFeedRepository = entireFeedRepository,
                authDataRepository = authDataRepository,
                feedDatabase = feedDatabase,
                sortBy = sortBy,
                emotion = emotion
            ),
            pagingSourceFactory = {
                feedDatabase.entireFeedDao.getAllFeedItems()
            }
        )
    }

    companion object {
        private const val ITEMS_PER_PAGE = 10
    }
}