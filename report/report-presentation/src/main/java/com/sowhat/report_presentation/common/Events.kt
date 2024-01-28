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
}