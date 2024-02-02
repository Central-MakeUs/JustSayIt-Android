package com.practice.database.dao

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.practice.database.entity.EntireFeedEntity
import com.practice.database.entity.MyFeedEntity

@Dao
interface EntireFeedDao {
    @Query("SELECT * FROM entire_feed")
    fun getAllFeedItems(): PagingSource<Int, EntireFeedEntity>

    @Query("SELECT * FROM entire_feed WHERE storyId=:storyId")
    suspend fun getFeedItemByFeedId(storyId: Long): EntireFeedEntity

    @Query("SELECT * FROM entire_feed WHERE storyUUID=:storyUUID")
    suspend fun getFeedItemByUUID(storyUUID: String): EntireFeedEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFeedItems(feedEntities: List<EntireFeedEntity>)

    @Query("DELETE FROM entire_feed")
    suspend fun deleteAllFeedItems()

    @Query("DELETE FROM entire_feed WHERE storyId=:storyId")
    suspend fun deleteFeedItemByFeedId(storyId: Long)

    @Query("DELETE FROM entire_feed WHERE storyUUID=:storyUUID")
    suspend fun deleteFeedItemByUUID(storyUUID: String)

    @Update
    suspend fun updateFeedItem(myFeedEntity: EntireFeedEntity)
}