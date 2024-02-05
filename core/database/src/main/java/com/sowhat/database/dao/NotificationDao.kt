package com.sowhat.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sowhat.database.entity.MyFeedEntity
import com.sowhat.database.entity.NotificationEntity
import java.util.UUID

@Dao
interface NotificationDao {
//    @Query("SELECT * FROM notification")
//    fun getAllData(): PagingSource<Int, NotificationEntity>

    @Query("SELECT * FROM notification")
    suspend fun getAllDataSynchronously(): List<NotificationEntity>

    @Query("SELECT * FROM notification WHERE notificationUUID=:notificationUUID")
    suspend fun getNotificationItemByUUID(notificationUUID: String): NotificationEntity

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addNotificationItems(notifications: List<NotificationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotificationItem(notifications: NotificationEntity)

    @Query("DELETE FROM notification")
    suspend fun deleteAll()

    @Query("DELETE FROM notification WHERE notificationUUID=:notificationUUID")
    suspend fun deleteNotificationItemByUUID(notificationUUID: String)

}