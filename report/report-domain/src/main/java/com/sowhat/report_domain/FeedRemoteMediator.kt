package com.sowhat.report_domain

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sowhat.database.FeedDatabase
import com.sowhat.database.entity.MyFeedEntity
import com.sowhat.report_domain.model.MyFeed
import com.sowhat.report_domain.repository.ReportRepository
import com.sowhat.common.model.Resource
import com.sowhat.datastore.AuthDataRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator(
    private val reportRepository: ReportRepository,
    private val authDataRepository: AuthDataRepository,
    private val feedDatabase: FeedDatabase,
    private val sortBy: String,
//    private val lastId: Long?,
//    private val hasNext: Boolean,
    private val emotion: String?
) : RemoteMediator<Int, MyFeedEntity>() {

    private val dao = feedDatabase.myFeedDao
    private var isFirst = true
    private var page = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MyFeedEntity>
    ): MediatorResult {
        return try {
            val authData = authDataRepository.authData.first()
            val accessToken = authData.accessToken

            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Log.i("FeedMediator", "load refresh: true")
                    // ***중요 : refresh될 때 스크롤 위치 이슈 해결 : 데이터 로드를 위해 로드키를 불러오는 과정에서 모든 데이터를 지워준다
                    dao.deleteAllMyFeedItems()
                    null
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    lastItem?.storyId
                    val lastItem = dao.getNthRecord(page * state.config.pageSize - 1)
                    page++
                    lastItem?.storyId ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            Log.i("FeedMediator", "load key : ${loadKey}")

            if (accessToken != null) {
                getPagingData(
                    accessToken = accessToken,
//                    memberId = memberId,
                    loadKey = loadKey,
                    state = state,
                    loadType = loadType,
                )
            } else MediatorResult.Error(NullPointerException())
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            MediatorResult.Error(e)
        }
    }

    private suspend fun getPagingData(
        accessToken: String,
//        memberId: Long,
        loadKey: Long?,
        state: PagingState<Int, MyFeedEntity>,
        loadType: LoadType,
    ): MediatorResult {
        val feedItems = reportRepository.getMyFeedList(
            accessToken = accessToken,
            sortBy = sortBy,
//            memberId = memberId,
            emotionCode = emotion,
            lastId = loadKey,
            size = state.config.pageSize
        )

        return getPagingDataTransactionResult(feedItems, loadType)
    }

    private suspend fun getPagingDataTransactionResult(
        feedItems: Resource<MyFeed>,
        loadType: LoadType
    ): MediatorResult {
        return when (feedItems) {
            is Resource.Success -> {
                addFeedItemsToDatabase(loadType, feedItems)

                MediatorResult.Success(
                    endOfPaginationReached = feedItems.data?.hasNext == false
                )
            }

            is Resource.Error -> {
                MediatorResult.Error(NullPointerException())
            }
        }
    }

    private suspend fun addFeedItemsToDatabase(
        loadType: LoadType,
        feedItems: Resource<MyFeed>
    ) {
        feedDatabase.withTransaction {
            if (loadType == LoadType.REFRESH) {
                dao.deleteAllMyFeedItems()
            }

            val myFeedEntities = feedItems.data?.feedItems
            myFeedEntities?.let {
                dao.addFeedItems(myFeedEntities)
            }
        }
    }
}