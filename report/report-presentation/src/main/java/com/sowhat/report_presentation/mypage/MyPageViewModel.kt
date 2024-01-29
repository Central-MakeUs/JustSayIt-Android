package com.sowhat.report_presentation.mypage

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.practice.database.entity.MyFeedEntity
import com.practice.report_domain.use_case.GetMyFeedUseCase
import com.sowhat.report_presentation.common.MyFeedEvent
import com.sowhat.report_presentation.common.MyFeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyFeedUseCase: GetMyFeedUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val myFeedUiState = savedStateHandle.getStateFlow(FEED_STATE, MyFeedUiState())

    private val sortBy = myFeedUiState.value.sortBy
//    private val lastId = myFeedUiState.value.lastId
//    private val hasNext = myFeedUiState.value.hasNext
    private val emotion = myFeedUiState.value.emotion

    @OptIn(ExperimentalCoroutinesApi::class)
    var myFeedPagingData: Flow<PagingData<MyFeedEntity>> =
        getMyFeedUseCase(
            sortBy = sortBy.postData,
            emotion = emotion.postData
        ).flow.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getMyFeedItems() {
        Log.i(TAG, "sortBy : $sortBy, emotion : $emotion")
        myFeedPagingData = getMyFeedUseCase(
            sortBy = myFeedUiState.value.sortBy.postData,
            emotion = myFeedUiState.value.emotion.postData
        ).flow.cachedIn(viewModelScope)
    }

    fun onEvent(event: MyFeedEvent) {
        when (event) {
            is MyFeedEvent.SortChanged -> {
                savedStateHandle[FEED_STATE] = savedStateHandle.get<MyFeedUiState>(FEED_STATE)?.copy(
                    sortBy = event.sort
                )
                getMyFeedItems()
            }
            is MyFeedEvent.HasNextChanged -> {
                savedStateHandle[FEED_STATE] = savedStateHandle.get<MyFeedUiState>(FEED_STATE)?.copy(
                    hasNext = event.hasNext
                )
            }
            is MyFeedEvent.LastIdChanged -> {
                savedStateHandle[FEED_STATE] = savedStateHandle.get<MyFeedUiState>(FEED_STATE)?.copy(
                    lastId = event.lastId
                )
            }
            is MyFeedEvent.EmotionChanged -> {
                savedStateHandle[FEED_STATE] = savedStateHandle.get<MyFeedUiState>(FEED_STATE)?.copy(
                    emotion = event.emotion,
                )
                Log.i(TAG, "emotion : ${myFeedUiState.value.emotion}")
                getMyFeedItems()
            }
            is MyFeedEvent.DropdownOpenChanged -> {
                savedStateHandle[FEED_STATE] = savedStateHandle.get<MyFeedUiState>(FEED_STATE)?.copy(
                    isDropdownOpen = event.isOpen
                )
            }
            is MyFeedEvent.FeedDeleteDialogChanged -> {
                savedStateHandle[FEED_STATE] = savedStateHandle.get<MyFeedUiState>(FEED_STATE)?.copy(
                    isDeleteDialogVisible = event.isVisible
                )
            }
            is MyFeedEvent.FeedTargetIdChanged -> {
                savedStateHandle[FEED_STATE] = savedStateHandle.get<MyFeedUiState>(FEED_STATE)?.copy(
                    targetId = event.targetId
                )
            }
        }
    }

    companion object {
        const val FEED_STATE = "feedState"
        const val TAG = "MyPage"
    }
}