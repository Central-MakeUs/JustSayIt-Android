package com.sowhat.report_presentation.mypage

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.practice.database.entity.MyFeedEntity
import com.practice.domain.use_case.DeleteFeedUseCase
import com.practice.report_domain.model.TodayMood
import com.practice.report_domain.use_case.GetMyFeedUseCase
import com.practice.report_domain.use_case.GetTodayMoodDataUseCase
import com.practice.report_domain.use_case.PostNewMoodUseCase
import com.sowhat.common.model.Resource
import com.sowhat.common.util.toTime
import com.sowhat.designsystem.common.MOOD_SAD
import com.sowhat.designsystem.common.MOOD_SURPRISED
import com.sowhat.designsystem.common.Mood
import com.sowhat.report_presentation.common.MyFeedEvent
import com.sowhat.report_presentation.common.MyFeedUiState
import com.sowhat.report_presentation.common.ReportEvent
import com.sowhat.report_presentation.common.ReportUiState
import com.sowhat.report_presentation.common.TodayMoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyFeedUseCase: GetMyFeedUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
    private val getTodayMoodDataUseCase: GetTodayMoodDataUseCase,
    private val postNewMoodUseCase: PostNewMoodUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val myFeedUiState = savedStateHandle.getStateFlow(FEED_STATE, MyFeedUiState())
    val reportUiState = savedStateHandle.getStateFlow(REPORT_STATE, ReportUiState())

    private val sortBy = MutableStateFlow(myFeedUiState.value.sortBy.postData)
    private val emotion = MutableStateFlow(myFeedUiState.value.emotion.postData)

    private val deleteEventChannel = Channel<Boolean>()
    val deleteEvent = deleteEventChannel.receiveAsFlow()

    private val postNewMoodEventChannel = Channel<Boolean>()
    val postNewMoodEvent = postNewMoodEventChannel.receiveAsFlow()

    init {
        getTodayMood()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    var myFeedPagingData: Flow<PagingData<MyFeedEntity>> = combine(sortBy, emotion) { s, e ->
        Pair(s, e)
    }.distinctUntilChanged().flatMapLatest { pair ->
        onMyFeedEvent(MyFeedEvent.LoadingChanged(true))
        Log.i(TAG, "browse data ${pair.first} ${pair.second}")
        getMyFeedUseCase(
            sortBy = pair.first,
            emotion = pair.second
        ).flow.map {
            onMyFeedEvent(MyFeedEvent.LoadingChanged(false))
            it
        }.cachedIn(viewModelScope)
    }

    fun deleteFeed(feedId: Long) {
        viewModelScope.launch {
            when (deleteFeedUseCase(feedId = feedId)) {
                is Resource.Success -> {
                    deleteEventChannel.send(true)
                }
                is Resource.Error -> {
                    deleteEventChannel.send(false)
                }
            }
        }
    }

    fun getTodayMood() {
        viewModelScope.launch {
            onReportEvent(ReportEvent.LoadingChanged(true))
            val result = getTodayMoodDataUseCase()
            when (result) {
                is Resource.Success -> {
                    savedStateHandle[REPORT_STATE] = getReportUiState()?.copy(
                        moodList = todayMoodItemList(result)
                    )
                    onReportEvent(ReportEvent.NicknameChanged(result.data?.memberName ?: ""))
                    onReportEvent(ReportEvent.LoadingChanged(false))
                }
                is Resource.Error -> {
                    onReportEvent(ReportEvent.LoadingChanged(false))
                }
            }
        }
    }

    fun postNewMood(mood: Mood?) {
        viewModelScope.launch {
            onReportEvent(ReportEvent.LoadingChanged(true))
            val postData = mood?.toPostData()
            Log.i(TAG, "postNewMood: $postData")
            postData?.let {
                when (postNewMoodUseCase(postData)) {
                    is Resource.Success -> {
                        postNewMoodEventChannel.send(true)
                    }
                    is Resource.Error -> {
                        postNewMoodEventChannel.send(false)
                    }
                }
            }
        }
    }

    private fun Mood.toPostData() = when (this) {
        Mood.ALL -> null
        Mood.HAPPY -> POST_HAPPY
        Mood.SAD -> POST_SAD
        Mood.SURPRISED -> POST_SURPRISED
        Mood.ANGRY -> POST_ANGRY
    }

    private fun todayMoodItemList(result: Resource<TodayMood>) =
        result.data?.myMoodRecord?.map { moodData ->
            TodayMoodItem(
                mood = Mood.values().find { it.moodCode == moodData.moodCode },
                time = moodData.createdAt.toTime()
            )
        } ?: emptyList()

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
            is MyFeedEvent.LoadingChanged -> {
                savedStateHandle[FEED_STATE] = getMyFeedUiState()?.copy(
                    isLoading = event.isLoading
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
//            is ReportEvent.Submit -> {
//                savedStateHandle[REPORT_STATE] = getReportUiState()?.copy(
//                    selectedMood = null,
//                )
//            }
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

            is ReportEvent.LoadingChanged -> {
                savedStateHandle[REPORT_STATE] = getReportUiState()?.copy(
                    isLoading = event.isLoading
                )
            }

            is ReportEvent.NicknameChanged -> {
                savedStateHandle[REPORT_STATE] = getReportUiState()?.copy(
                    nickname = event.nickname
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
        const val POST_HAPPY = "MOOD001"
        const val POST_SAD = "MOOD002"
        const val POST_SURPRISED = "MOOD003"
        const val POST_ANGRY = "MOOD004"
    }
}

