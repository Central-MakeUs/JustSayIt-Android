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
