package com.sowhat.feed_presentation.feeds

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sowhat.feed_presentation.common.FeedAppBarEvent
import com.sowhat.feed_presentation.common.FeedAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(

) : ViewModel() {

    var appBarState by mutableStateOf(FeedAppBarState())
        private set

    fun onAppBarEvent(appBarEvent: FeedAppBarEvent) {
        when (appBarEvent) {
            is FeedAppBarEvent.EmotionChanged -> {
                appBarState = appBarState.copy(
                    currentEmotion = appBarEvent.mood
                )
            }
            is FeedAppBarEvent.SortChanged -> {
                appBarState = appBarState.copy(
                    selectedTabItem = appBarEvent.sortBy
                )
            }
            is FeedAppBarEvent.DropdownExpandChanged -> {
                appBarState = appBarState.copy(
                    isDropdownExpanded = appBarEvent.isOpen
                )
            }
        }
    }
}