package com.sowhat.post_presentation.common

import android.net.Uri
import android.os.Parcelable
import com.sowhat.designsystem.common.MoodItem
import kotlinx.parcelize.Parcelize

data class PostFormState(
    val currentMood: MoodItem? = null,
    val isCurrentMoodValid: Boolean = false,
    val postText: String = "",
    val isPostTextValid: Boolean = false,
    val images: List<Uri> = emptyList(),
    val isImageListValid: Boolean = true,
    val isOpened: Boolean = false,
    val isAnonymous: Boolean = false,
    val sympathyMoodItems: List<MoodItem> = emptyList(),
    val isSympathyMoodItemsValid: Boolean = true,
    val isDialogVisible: Boolean = false,
)

data class EditFormState(
    val isChanged: Boolean = false,
    val storyId: Long? = null,
    val existingUrl: List<String> = emptyList(),
    val existingUrlId: List<Long> = emptyList(),
    val deletedUrlId: List<Long> = emptyList(),
    val currentMood: MoodItem? = null,
    val isCurrentMoodValid: Boolean = false,
    val postText: String = "",
    val isPostTextValid: Boolean = false,
    val images: List<Uri> = emptyList(),
    val isImageListValid: Boolean = true,
    val isOpened: Boolean = false,
    val isAnonymous: Boolean = false,
    val sympathyMoodItems: List<MoodItem> = emptyList(),
    val isSympathyMoodItemsValid: Boolean = true,
    val isDialogVisible: Boolean = false,
)