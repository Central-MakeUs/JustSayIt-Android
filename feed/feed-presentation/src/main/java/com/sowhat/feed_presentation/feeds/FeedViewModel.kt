package com.sowhat.feed_presentation.feeds

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sowhat.feed_domain.use_case.GetEntireFeedUseCase
import com.sowhat.feed_presentation.common.FeedAppBarEvent
import com.sowhat.feed_presentation.common.FeedListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getEntireFeedUseCase: GetEntireFeedUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var feedListState = savedStateHandle.getStateFlow(FEED_LIST_STATE, FeedListState())
        private set

    private val sortBy = MutableStateFlow(feedListState.value.selectedTabItem.postData)
    private val emotion = MutableStateFlow(feedListState.value.currentEmotion.postData)

    @OptIn(ExperimentalCoroutinesApi::class)
    val entireFeedData = combine(sortBy, emotion) { s, e ->
        Log.i("FeedScreen", "Feed: $s, $e")
        Pair(s, e)
    }.distinctUntilChanged().flatMapLatest { pair ->
        getEntireFeedUseCase(
            sortBy = pair.first,
            emotion = pair.second
        ).flow.cachedIn(viewModelScope)
    }

    fun onAppBarEvent(appBarEvent: FeedAppBarEvent) {
        when (appBarEvent) {
            is FeedAppBarEvent.EmotionChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    currentEmotion = appBarEvent.mood
                )
                emotion.value = appBarEvent.mood.postData
            }
            is FeedAppBarEvent.SortChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    selectedTabItem = appBarEvent.sortBy
                )
                sortBy.value = appBarEvent.sortBy.postData
            }
            is FeedAppBarEvent.DropdownExpandChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    isDropdownExpanded = appBarEvent.isOpen
                )
            }
        }
    }

    private fun getFeedAppBarState() = savedStateHandle.get<FeedListState>(FEED_LIST_STATE)

    companion object {
        private const val FEED_LIST_STATE = "appbar_state"
        private const val FEED_STATE = "feed_state"
    }
}