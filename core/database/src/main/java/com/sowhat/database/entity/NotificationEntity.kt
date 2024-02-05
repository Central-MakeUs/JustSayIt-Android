package com.sowhat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sowhat.database.common.TABLE_ENTIRE_FEED
import com.sowhat.database.common.TABLE_NOTIFICATION
import java.util.UUID

@Entity(tableName = TABLE_NOTIFICATION)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = false)
    val notificationUUID: String = UUID.randomUUID().toString(),
    val title: String,
    val body: String,
    val targetCategory: String,
    val targetData: String,
    val date: String?
)
