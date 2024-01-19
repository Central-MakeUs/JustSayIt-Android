package com.sowhat.feed_presentation.common

import com.sowhat.designsystem.common.DropdownItem


sealed class FeedAppBarEvent {
    data class EmotionChanged(val dropdown: DropdownItem) : FeedAppBarEvent()
    data class SortChanged(val sortBy: String) : FeedAppBarEvent()
    data class DropdownExpandChanged(val isOpen: Boolean) : FeedAppBarEvent()
}

sealed class FeedItemEvent {
    // TODO 서버의 데이터에 따라 수정 필요
    object FeedItemClicked : FeedItemEvent()
}