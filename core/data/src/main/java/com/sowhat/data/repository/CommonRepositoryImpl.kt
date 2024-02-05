package com.sowhat.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.sowhat.data.remote.CommonApi
import com.sowhat.database.FeedDatabase
import com.sowhat.domain.repository.CommonRepository
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

            Log.i("Delete", "deleteFeed: database transaction successful")

            Resource.Success(
                data = result.data,
                code = result.code,
                message = result.message
            )
        } else {
            Log.i("Delete", "deleteFeed: transaction error")

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