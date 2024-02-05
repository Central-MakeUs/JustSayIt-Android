package com.sowhat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sowhat.database.common.TABLE_MY_FEED

@Entity(tableName = TABLE_MY_FEED)
data class MyFeedEntity(
    @PrimaryKey(autoGenerate = false)
    val storyUUID: String,
    val storyId: Long,
    val createdAt: String,
    val updatedAt: String,
    val writerId: Long,
    val totalCount: Long,
    val happinessCount: Long?,
    val sadnessCount: Long?,
    val surprisedCount: Long?,
    val angryCount: Long?,
    val isHappinessSelected: Boolean,
    val isSadnessSelected: Boolean,
    val isSurprisedSelected: Boolean,
    val isAngrySelected: Boolean,
    val nickname: String,
    val profileImg: String,
    val bodyText: String,
    val photo: List<String>,
    val writerEmotion: String,
    val isAnonymous: Boolean,
    val isModified: Boolean,
    val isOpened: Boolean
)
