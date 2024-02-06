package com.sowhat.post_presentation.common

import android.net.Uri
import com.sowhat.designsystem.common.MoodItem
import okhttp3.MultipartBody

sealed class PostFormEvent {
    data class CurrentMoodChanged(val mood: MoodItem) : PostFormEvent()
    data class ImageListUpdated(val images: List<Uri>?) : PostFormEvent()
    data class PostTextChanged(val text: String) : PostFormEvent()
    data class OpenChanged(val open: Boolean) : PostFormEvent()
    data class AnonymousChanged(val anonymous: Boolean) : PostFormEvent()
    data class SympathyItemsChanged(val sympathyItems: List<MoodItem>) : PostFormEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : PostFormEvent()
}

sealed class EditFormEvent {
    data class DeletedUrlIdAdded(val urlIds: List<Long>) : EditFormEvent()
    data class CurrentMoodChanged(val mood: MoodItem) : EditFormEvent()
    data class ImageListUpdated(val images: List<Uri>?) : EditFormEvent()
    data class PostTextChanged(val text: String) : EditFormEvent()
    data class OpenChanged(val open: Boolean) : EditFormEvent()
    data class AnonymousChanged(val anonymous: Boolean) : EditFormEvent()
    data class SympathyItemsChanged(val sympathyItems: List<MoodItem>) : EditFormEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : EditFormEvent()
}
