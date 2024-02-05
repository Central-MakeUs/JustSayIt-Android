package com.sowhat.feed_domain

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sowhat.database.FeedDatabase
import com.sowhat.database.entity.EntireFeedEntity
import com.sowhat.common.model.Resource
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.feed_domain.model.EntireFeed
import com.sowhat.feed_domain.repository.EntireFeedRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException


@OptIn(ExperimentalPagingApi::class)
class EntireFeedRemoteMediator(
    private val entireFeedRepository: EntireFeedRepository,
    private val authDataRepository: AuthDataRepository,
    private val feedDatabase: FeedDatabase,
    private val sortBy: String,
    private val emotion: String?
) : RemoteMediator<Int, EntireFeedEntity>() {

    private val dao = feedDatabase.entireFeedDao
    private var page = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EntireFeedEntity>
    ): MediatorResult {
        return try {
            val authData = authDataRepository.authData.first()
            val accessToken = authData.accessToken

            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Log.i("EntireFeedMediator", "load: refresh")
                    // ***중요 : refresh될 때 스크롤 위치 이슈 해결 : 데이터 로드를 위해 로드키를 불러오는 과정에서 모든 데이터를 지워준다
                    // 이슈 : 주석처리 시, 새로고침 시 다음 페이지가 호출되지 않음
//                    feedDatabase.withTransaction {
//                        dao.deleteAllFeedItems()
//                    }
                    null
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = dao.getNthRecord(page * state.config.pageSize - 1)
                    page++
                    lastItem?.storyId ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

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
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private suspend fun getPagingData(
        accessToken: String,
//        memberId: Long,
        loadKey: Long?,
        state: PagingState<Int, EntireFeedEntity>,
        loadType: LoadType,
    ): MediatorResult {
        val feedItems = entireFeedRepository.getEntireFeedList(
            accessToken = accessToken,
            sortBy = sortBy,
//            memberId = memberId,
            emotionCode = emotion,
            lastId = loadKey,
            size = state.config.pageSize
        )

        Log.i("EntireFeedMediator", "getPagingData: success")

        return getPagingDataTransactionResult(feedItems, loadType)
    }

    private suspend fun getPagingDataTransactionResult(
        feedItems: Resource<EntireFeed>,
        loadType: LoadType
    ): MediatorResult {
        return when (feedItems) {
            is Resource.Success -> {
                Log.i("EntireFeedMediator", "getPagingDataTransactionResult: success")
                addFeedItemsToDatabase(loadType, feedItems)

                MediatorResult.Success(
                    endOfPaginationReached = feedItems.data?.hasNext == false
                )
            }

            is Resource.Error -> {
                Log.i("EntireFeedMediator", "getPagingDataTransactionResult: error")
                MediatorResult.Error(NullPointerException())
            }
        }
    }

    private suspend fun addFeedItemsToDatabase(
        loadType: LoadType,
        feedItems: Resource<EntireFeed>
    ) {
        feedDatabase.withTransaction {
            Log.i("EntireFeedMediator", "addFeedItemsToDatabase: start adding")
            if (loadType == LoadType.REFRESH) {
                dao.deleteAllFeedItems()
            }

            val myFeedEntities = feedItems.data?.feedItems
            myFeedEntities?.let {
                Log.i("EntireFeedMediator", "addFeedItemsToDatabase: adding $myFeedEntities")
                dao.addFeedItems(myFeedEntities)
                Log.i("EntireFeedMediator", "addFeedItemsToDatabase: added")
            }
        }
    }
}
//삭제한 배열 : []
//[1, 2, 3, 4]
// a  b  c  d
//만약에 a를 지웠어 -> 삭제한 배열에 삭제한 사진의 아이디를 삽입
//새로운 사진 -> 멀티파트
//[1], [멀티파트~~~]