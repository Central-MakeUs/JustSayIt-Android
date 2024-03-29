package com.sowhat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sowhat.database.common.TABLE_ENTIRE_FEED

@Entity(tableName = TABLE_ENTIRE_FEED)
data class EntireFeedEntity(
    @PrimaryKey(autoGenerate = false)
    val storyUUID: String,
    val storyId: Long,
    val createdAt: String,
    val updatedAt: String,
    val writerId: Long,
    val totalCount: Long,
    var happinessCount: Long?,
    var sadnessCount: Long?,
    var surprisedCount: Long?,
    var angryCount: Long?,
    val isHappinessSelected: Boolean,
    val isSadnessSelected: Boolean,
    val isSurprisedSelected: Boolean,
    val isAngrySelected: Boolean,
    val nickname: String,
    val profileImg: String,
    val bodyText: String,
    val photo: List<String>,
    val photoId: List<Long>?,
    val writerEmotion: String,
    val isAnonymous: Boolean,
    val isModified: Boolean,
    val isOpened: Boolean,
    var selectedEmotionCode: String?,
    val isOwner: Boolean?
)
