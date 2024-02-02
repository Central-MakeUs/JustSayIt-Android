package com.practice.data.repository

import androidx.room.withTransaction
import com.practice.data.remote.CommonApi
import com.practice.database.FeedDatabase
import com.practice.domain.repository.CommonRepository
import com.sowhat.common.model.Resource
import com.sowhat.network.util.getHttpErrorResource
import com.sowhat.network.util.getIOErrorResource
import retrofit2.HttpException
import java.io.IOException

class CommonRepositoryImpl(
    private val feedDatabase: FeedDatabase,
    private val commonApi: CommonApi
) : CommonRepository {
    override suspend fun deleteFeed(accessToken: String, feedId: Long): Resource<Unit?> = try {
        val myFeedDao = feedDatabase.myFeedDao
        val entireFeedDao = feedDatabase.entireFeedDao

        val result = commonApi.deleteFeed(accessToken, feedId)

        if (result.isSuccess) {
            feedDatabase.withTransaction {
                myFeedDao.deleteMyFeedItemByFeedId(feedId)
                entireFeedDao.deleteFeedItemByFeedId(feedId)
            }

            Resource.Success(
                data = result.data,
                code = result.code,
                message = result.message
            )
        } else {
            Resource.Error(
                code = result.code,
                message = result.message
            )
        }
    } catch (e: HttpException) {
        getHttpErrorResource(e)
    } catch (e: IOException) {
        getIOErrorResource(e)
    }
}