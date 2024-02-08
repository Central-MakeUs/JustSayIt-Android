package com.sowhat.feed_presentation.feeds

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sowhat.domain.use_case.DeleteFeedUseCase
import com.sowhat.common.model.Resource
import com.sowhat.common.model.UiState
import com.sowhat.database.FeedDatabase
import com.sowhat.feed_domain.use_case.BlockUserUseCase
import com.sowhat.feed_domain.use_case.CancelEmpathyUseCase
import com.sowhat.feed_domain.use_case.GetEntireFeedUseCase
import com.sowhat.feed_domain.use_case.PostEmpathyUseCase
import com.sowhat.feed_domain.use_case.ReportFeedUseCase
import com.sowhat.feed_presentation.common.FeedEvent
import com.sowhat.feed_presentation.common.FeedListState
import com.sowhat.feed_presentation.common.PostResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getEntireFeedUseCase: GetEntireFeedUseCase,
    private val reportFeedUseCase: ReportFeedUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
    private val postEmpathyUseCase: PostEmpathyUseCase,
    private val cancelEmpathyUseCase: CancelEmpathyUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val database: FeedDatabase
) : ViewModel() {

    var feedListState = savedStateHandle.getStateFlow(FEED_LIST_STATE, FeedListState())
        private set

    var uiState = MutableStateFlow<UiState<Unit>>(UiState())
        private set

    private val sortBy = MutableStateFlow(feedListState.value.selectedTabItem.postData)
    private val emotion = MutableStateFlow(feedListState.value.currentEmotion.postData)

    private val reportEventChannel = Channel<Boolean>()
    val reportEvent = reportEventChannel.receiveAsFlow()

    private val blockEventChannel = Channel<Boolean>()
    val blockEvent = blockEventChannel.receiveAsFlow()

    private val deleteEventChannel = Channel<Boolean>()
    val deleteEvent = deleteEventChannel.receiveAsFlow()

    private val empathyEventChannel = Channel<PostResult>()
    val empathyEvent = empathyEventChannel.receiveAsFlow()

    override fun onCleared() {
        super.onCleared()
        Log.i("FeedViewModel", "onCleared")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val entireFeedData = combine(sortBy, emotion) { s, e ->
        Log.i("FeedScreen", "Feed: $s, $e")
        Pair(s, e)
    }.distinctUntilChanged().flatMapLatest { pair ->
        uiState.update { it.copy(isLoading = true) }
        getEntireFeedUseCase(
            sortBy = pair.first,
            emotion = pair.second
        ).flow.cachedIn(viewModelScope)
    }

    fun reportFeed(
        feedId: Long,
        reportCode: String
    ) {
        viewModelScope.launch {
            uiState.update { it.copy(isLoading = true) }
            when (reportFeedUseCase(feedId = feedId, reportCode = reportCode)) {
                is Resource.Success -> {
                    reportEventChannel.send(true)
                    uiState.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> {
                    reportEventChannel.send(false)
                    uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun blockUser(userId: Long) {
        uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (blockUserUseCase(userId = userId)) {
                is Resource.Success -> {
                    blockEventChannel.send(true)
                    uiState.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> {
                    blockEventChannel.send(false)
                    uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun deleteFeed(feedId: Long) {
        uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (deleteFeedUseCase(feedId = feedId)) {
                is Resource.Success -> {
                    deleteEventChannel.send(true)
                    uiState.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> {
                    deleteEventChannel.send(false)
                    uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun postEmpathy(
        feedId: Long,
        emotionCode: String,
        index: Int
    ) {
        Log.i("FeedScreen", "post emotionCode : $emotionCode")
//        uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (postEmpathyUseCase(feedId, emotionCode)) {
                is Resource.Success -> {
                    empathyEventChannel.send(PostResult(true, emotionCode, index))
//                    uiState.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> {
                    empathyEventChannel.send(PostResult(false, null, index))
//                    uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun cancelEmpathy(
        feedId: Long,
        previousEmpathy: String?,
        index: Int
    ) {
//        uiState.update { it.copy(isLoading = true) }
        Log.i("FeedScreen", "cancel emotionCode : $previousEmpathy")
        viewModelScope.launch {
            when (cancelEmpathyUseCase(feedId, previousEmpathy)) {
                is Resource.Success -> {
                    empathyEventChannel.send(PostResult(true, null, index))
//                    uiState.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> {
                    empathyEventChannel.send(PostResult(false, null, index))
//                    uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    suspend fun refreshFeeds() {
        viewModelScope.launch {
            val dao = database.entireFeedDao
            dao.deleteAllFeedItems()
        }
    }

    fun onFeedEvent(feedEvent: FeedEvent) {
        when (feedEvent) {
            is FeedEvent.EmotionChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    currentEmotion = feedEvent.mood
                )
                emotion.value = feedEvent.mood.postData
            }
            is FeedEvent.SortChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    selectedTabItem = feedEvent.sortBy
                )
                sortBy.value = feedEvent.sortBy.postData
            }
            is FeedEvent.DropdownExpandChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    isDropdownExpanded = feedEvent.isOpen
                )
            }
            is FeedEvent.BlockDialogVisibilityChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    isBlockDialogVisible = feedEvent.isVisible
                )
            }
            is FeedEvent.DeleteDialogVisibilityChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    isDeleteDialogVisible = feedEvent.isVisible
                )
            }
            is FeedEvent.ReportDialogVisibilityChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    isReportDialogVisible = feedEvent.isVisible
                )
            }
            is FeedEvent.TargetIdChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    targetId = feedEvent.targetId
                )
            }
            is FeedEvent.ReportPostDataChange -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    reportPostData = feedEvent.postData
                )
            }
            is FeedEvent.ImageDialogVisibilityChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    isImageDialogVisible = feedEvent.isVisible
                )
            }
            is FeedEvent.ImageUrlChanged -> {
                savedStateHandle[FEED_LIST_STATE] = getFeedAppBarState()?.copy(
                    imageUrl = feedEvent.imageUrl
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