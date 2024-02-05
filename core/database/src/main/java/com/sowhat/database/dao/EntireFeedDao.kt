package com.sowhat.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.sowhat.database.entity.EntireFeedEntity

@Dao
interface EntireFeedDao {
    @Query("SELECT * FROM entire_feed")
    fun getAllFeedItems(): PagingSource<Int, EntireFeedEntity>

    @Query("SELECT * FROM entire_feed WHERE storyId=:storyId")
    suspend fun getFeedItemByFeedId(storyId: Long): EntireFeedEntity

    @Query("SELECT * FROM entire_feed WHERE storyUUID=:storyUUID")
    suspend fun getFeedItemByUUID(storyUUID: String): EntireFeedEntity

    @Upsert
    suspend fun addFeedItems(feedEntities: List<EntireFeedEntity>)

    @Query("DELETE FROM entire_feed")
    suspend fun deleteAllFeedItems()

    @Query("DELETE FROM entire_feed WHERE storyId=:storyId")
    suspend fun deleteFeedItemByFeedId(storyId: Long)

    @Query("DELETE FROM entire_feed WHERE storyUUID=:storyUUID")
    suspend fun deleteFeedItemByUUID(storyUUID: String)

    @Query("DELETE FROM entire_feed WHERE writerId=:userId")
    suspend fun deleteFeedItemByUserId(userId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFeedItem(myFeedEntity: EntireFeedEntity)

    @Query("SELECT * FROM entire_feed LIMIT 1 OFFSET :offset")
    suspend fun getNthRecord(offset: Int): EntireFeedEntity?
}