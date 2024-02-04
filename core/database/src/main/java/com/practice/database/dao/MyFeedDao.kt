package com.practice.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.practice.database.entity.MyFeedEntity

@Dao
interface MyFeedDao {
    @Query("SELECT * FROM my_feed")
    fun getAllMyFeedItems(): PagingSource<Int, MyFeedEntity>

    @Query("SELECT * FROM my_feed WHERE storyId=:storyId")
    suspend fun getFeedItemByFeedId(storyId: Long): MyFeedEntity

    @Query("SELECT * FROM my_feed WHERE storyUUID=:storyUUID")
    suspend fun getFeedItemByUUID(storyUUID: String): MyFeedEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFeedItems(feedEntities: List<MyFeedEntity>)

    @Query("DELETE FROM my_feed")
    suspend fun deleteAllMyFeedItems()

    @Query("DELETE FROM my_feed WHERE storyId=:storyId")
    suspend fun deleteMyFeedItemByFeedId(storyId: Long)

    @Query("DELETE FROM my_feed WHERE storyUUID=:storyUUID")
    suspend fun deleteMyFeedItemByUUID(storyUUID: String)

    @Update
    suspend fun updateMyFeedItem(myFeedEntity: MyFeedEntity)

    @Query("SELECT * FROM my_feed LIMIT 1 OFFSET :offset")
    suspend fun getNthRecord(offset: Int): MyFeedEntity?

}