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
import com.sowhat.report_presentation.common.ReportEvent
import com.sowhat.report_presentation.common.ReportUiState
import com.sowhat.report_presentation.common.TodayMoodItem
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
    val reportUiState = savedStateHandle.getStateFlow(REPORT_STATE, ReportUiState())

    private val sortBy = MutableStateFlow(myFeedUiState.value.sortBy.postData)
    private val emotion = MutableStateFlow(myFeedUiState.value.emotion.postData)

    @OptIn(ExperimentalCoroutinesApi::class)
    var myFeedPagingData: Flow<PagingData<MyFeedEntity>> = combine(sortBy, emotion) { s, e ->
        Pair(s, e)
    }.distinctUntilChanged().flatMapLatest { pair ->
        Log.i(TAG, "browse data ${pair.first} ${pair.second}")
        getMyFeedUseCase(
            sortBy = pair.first,
            emotion = pair.second
        ).flow.cachedIn(viewModelScope)
    }

    fun onMyFeedEvent(event: MyFeedEvent) {
        when (event) {
            is MyFeedEvent.SortChanged -> {
                savedStateHandle[FEED_STATE] = getMyFeedUiState()?.copy(
                    sortBy = event.sort
                )
                sortBy.value = myFeedUiState.value.sortBy.postData
            }
            is MyFeedEvent.HasNextChanged -> {
                savedStateHandle[FEED_STATE] = getMyFeedUiState()?.copy(
                    hasNext = event.hasNext
                )
            }
            is MyFeedEvent.LastIdChanged -> {
                savedStateHandle[FEED_STATE] = getMyFeedUiState()?.copy(
                    lastId = event.lastId
                )
            }
            is MyFeedEvent.EmotionChanged -> {
                savedStateHandle[FEED_STATE] = getMyFeedUiState()?.copy(
                    emotion = event.emotion,
                )
                Log.i(TAG, "emotion : ${myFeedUiState.value.emotion}")
                emotion.value = myFeedUiState.value.emotion.postData
            }
            is MyFeedEvent.DropdownOpenChanged -> {
                savedStateHandle[FEED_STATE] = getMyFeedUiState()?.copy(
                    isDropdownOpen = event.isOpen
                )
            }
            is MyFeedEvent.FeedDeleteDialogChanged -> {
                savedStateHandle[FEED_STATE] = getMyFeedUiState()?.copy(
                    isDeleteDialogVisible = event.isVisible
                )
            }
            is MyFeedEvent.FeedTargetIdChanged -> {
                savedStateHandle[FEED_STATE] = getMyFeedUiState()?.copy(
                    targetId = event.targetId
                )
            }
        }
    }

    fun onReportEvent(event: ReportEvent) {
        when (event) {
            is ReportEvent.SelectedMoodChanged -> {
                savedStateHandle[REPORT_STATE] = getReportUiState()?.copy(
                    selectedMood = event.mood,
                )
            }
            is ReportEvent.Submit -> {
                savedStateHandle[REPORT_STATE] = getReportUiState()?.copy(
                    selectedMood = null,
                )
            }
            is ReportEvent.TodayMoodAdded -> {
                val currentList = getReportUiState()?.moodList?.toMutableList()
                if (currentList != null) {
                    savedStateHandle[REPORT_STATE] = getReportUiState()?.copy(
                        moodList = currentList + TodayMoodItem(event.mood, event.time)
                    )
                }
            }
            is ReportEvent.SubmitActiveChanged -> {
                savedStateHandle[REPORT_STATE] = getReportUiState()?.copy(
                    isSubmitEnabled = event.isValid
                )
            }
        }
    }

    private fun getMyFeedUiState(): MyFeedUiState? = savedStateHandle.get<MyFeedUiState>(FEED_STATE)
    private fun getReportUiState(): ReportUiState? = savedStateHandle.get<ReportUiState>(REPORT_STATE)

    companion object {
        const val FEED_STATE = "feedState"
        const val REPORT_STATE = "reportState"
        const val TAG = "MyPage"
    }
}

