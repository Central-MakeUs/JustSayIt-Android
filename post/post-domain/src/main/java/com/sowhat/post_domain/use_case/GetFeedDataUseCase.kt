package com.sowhat.post_domain.use_case

import com.sowhat.database.FeedDatabase
import com.sowhat.database.entity.MyFeedEntity
import java.io.IOException
import javax.inject.Inject

class GetFeedDataUseCase @Inject constructor(
    private val feedDatabase: FeedDatabase
) {
    suspend operator fun invoke(feedId: Long): MyFeedEntity? {
        return try {
            val dao = feedDatabase.myFeedDao
            dao.getFeedItemByFeedId(feedId)
        } catch (e: Exception) {
            null
        }
    }
}