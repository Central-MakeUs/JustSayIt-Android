package com.sowhat.report_presentation.common

import com.sowhat.designsystem.common.DropdownItem
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.TabItem

sealed class MyFeedEvent {
    data class SortChanged(val sort: TabItem) : MyFeedEvent()
    data class LastIdChanged(val lastId: Long) : MyFeedEvent()
    data class HasNextChanged(val hasNext: Boolean) : MyFeedEvent()
    data class EmotionChanged(val emotion: Mood) : MyFeedEvent()
    data class DropdownOpenChanged(val isOpen: Boolean) : MyFeedEvent()
    data class FeedDeleteDialogChanged(val isVisible: Boolean) : MyFeedEvent()
    data class FeedTargetIdChanged(val targetId: Long?) : MyFeedEvent()
    data class LoadingChanged(val isLoading: Boolean) : MyFeedEvent()
}

sealed class ReportEvent {
    data class SelectedMoodChanged(val mood: Mood?) : ReportEvent()
    object Submit : ReportEvent()
    data class TodayMoodAdded(val mood: Mood, val time: String) : ReportEvent()
    data class SubmitActiveChanged(val isValid: Boolean) : ReportEvent()
}