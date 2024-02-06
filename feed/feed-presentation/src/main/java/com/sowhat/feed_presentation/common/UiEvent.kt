package com.sowhat.feed_presentation.common

import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.TabItem


sealed class FeedEvent {
    data class ImageDialogVisibilityChanged(val isVisible: Boolean) : FeedEvent()
    data class ImageUrlChanged(val imageUrl: String?) : FeedEvent()
    data class EmotionChanged(val mood: Mood) : FeedEvent()
    data class SortChanged(val sortBy: TabItem) : FeedEvent()
    data class DropdownExpandChanged(val isOpen: Boolean) : FeedEvent()
    data class DeleteDialogVisibilityChanged(val isVisible: Boolean) : FeedEvent()
    data class ReportDialogVisibilityChanged(val isVisible: Boolean) : FeedEvent()
    data class BlockDialogVisibilityChanged(val isVisible: Boolean) : FeedEvent()
    data class TargetIdChanged(val targetId: Long?) : FeedEvent()
    data class ReportPostDataChange(val postData: String?) : FeedEvent()
}

sealed class FeedItemEvent {
    // TODO 서버의 데이터에 따라 수정 필요
    object FeedItemClicked : FeedItemEvent()
}