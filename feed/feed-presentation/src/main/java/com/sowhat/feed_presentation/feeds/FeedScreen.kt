package com.sowhat.feed_presentation.feeds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.practice.database.entity.EntireFeedEntity
import com.sowhat.common.model.UiState
import com.sowhat.common.util.ObserveEvents
import com.sowhat.designsystem.common.MOOD_ANGRY
import com.sowhat.designsystem.common.MOOD_HAPPY
import com.sowhat.designsystem.common.MOOD_SAD
import com.sowhat.designsystem.common.MOOD_SURPRISED
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.component.AppBarFeed
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.feed_presentation.common.FeedEvent
import com.sowhat.feed_presentation.common.FeedListState
import com.sowhat.designsystem.common.isScrollingUp
import com.sowhat.designsystem.common.rememberMoodItemsForFeed
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.R
import com.sowhat.designsystem.component.AlertDialogReverse
import com.sowhat.designsystem.component.PopupMenuItem
import com.sowhat.designsystem.component.SelectionAlertDialog
import com.sowhat.feed_presentation.component.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FeedRoute(
    navController: NavController,
    viewModel: FeedViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val feedListState = viewModel.feedListState.collectAsState().value
    val feedPagingData = viewModel.entireFeedData.collectAsLazyPagingItems()
    val uiState = viewModel.uiState.collectAsState().value
    val scope = rememberCoroutineScope()

    PostEventObserver(
        viewModel,
        scope,
        snackbarHostState,
        feedPagingData
    )

    FeedScreen(
        navController = navController,
        feedListState = feedListState,
        onFeedEvent = viewModel::onFeedEvent,
        feedPagingData = feedPagingData,
        uiState = uiState,
    )

    if (feedListState.isReportDialogVisible) {
        val items = stringArrayResource(id = R.array.report_reason).toList()
        val postItems = stringArrayResource(id = R.array.report_reason_post).toList()
        var selectedItem by remember {
            mutableStateOf<String?>(null)
        }

        SelectionAlertDialog(
            title = stringResource(id = R.string.dialog_title_report),
            subTitle = stringResource(id = R.string.dialog_subtitle_report),
            selectedItem = selectedItem,
            selectionItems = items,
            buttonText = stringResource(id = R.string.dialog_button_report),
            onChange = { changedItem ->
                selectedItem = changedItem
                val index = items.indexOf(changedItem)
                viewModel.onFeedEvent(FeedEvent.ReportPostDataChange(postItems[index]))
            },
            onAccept = {
                viewModel.onFeedEvent(FeedEvent.ReportDialogVisibilityChanged(false))
                if (feedListState.targetId != null && feedListState.reportPostData != null) {
                    viewModel.reportFeed(feedListState.targetId, feedListState.reportPostData)
                    viewModel.onFeedEvent(FeedEvent.ReportPostDataChange(null))
                    viewModel.onFeedEvent(FeedEvent.TargetIdChanged(null))
                }
            },
            onDismiss = {
                viewModel.onFeedEvent(FeedEvent.ReportDialogVisibilityChanged(false))
                viewModel.onFeedEvent(FeedEvent.TargetIdChanged(null))
            }
        )
    }

    if (feedListState.isBlockDialogVisible) {
        AlertDialogReverse(
            title = stringResource(id = R.string.dialog_title_block),
            subTitle = stringResource(id = R.string.dialog_subtitle_block),
            buttonContent = stringResource(
                id = R.string.dialog_button_cancel
            ) to stringResource(id = R.string.dialog_button_block),
            onAccept = {
                viewModel.onFeedEvent(FeedEvent.BlockDialogVisibilityChanged(false))
                feedListState.targetId?.let {
                    viewModel.blockUser(feedListState.targetId)
                    viewModel.onFeedEvent(FeedEvent.TargetIdChanged(null))
                }
            },
            onDismiss = {
                viewModel.onFeedEvent(FeedEvent.BlockDialogVisibilityChanged(false))
                viewModel.onFeedEvent(FeedEvent.TargetIdChanged(null))
            }
        )
    }

    if (feedListState.isDeleteDialogVisible) {

    }
}

@Composable
private fun PostEventObserver(
    viewModel: FeedViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    feedPagingData: LazyPagingItems<EntireFeedEntity>
) {
    val blockSuccessMessage = stringResource(id = R.string.snackbar_block_successful)
    val blockFailureMessage = stringResource(id = R.string.snackbar_block_failure)
    val reportSuccessMessage = stringResource(id = R.string.snackbar_report_successful)
    val reportFailureMessage = stringResource(id = R.string.snackbar_report_failure)

    ObserveEvents(flow = viewModel.reportEvent) { isSuccessful ->
        scope.launch {
            if (isSuccessful) {
                snackbarHostState.showSnackbar(
                    message = reportSuccessMessage,
                    duration = SnackbarDuration.Short
                )
            } else {
                snackbarHostState.showSnackbar(
                    message = reportFailureMessage,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    ObserveEvents(flow = viewModel.blockEvent) { isSuccessful ->
        scope.launch {
            if (isSuccessful) {
                snackbarHostState.showSnackbar(
                    message = blockSuccessMessage,
                    duration = SnackbarDuration.Short
                )
                feedPagingData.refresh()
            } else {
                snackbarHostState.showSnackbar(
                    message = blockFailureMessage,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: UiState<Unit>,
    feedListState: FeedListState,
    onFeedEvent: (FeedEvent) -> Unit,
    feedPagingData: LazyPagingItems<EntireFeedEntity>
) {
    TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground),
        topBar = {
            AnimatedAppBar(lazyListState, feedListState, onFeedEvent)
        },
        bottomBar = {},
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(JustSayItTheme.Colors.mainBackground)
                .padding(paddingValues),
            state = lazyListState
        ){
            items(
                count = feedPagingData.itemCount,
                key = feedPagingData.itemKey { item -> item.storyId },
                contentType = { "Feed" }
            ) { index ->
                val feed = feedPagingData[index]

                val sympathyItems = getValidatedSympathyItems(feed)
                var selectedSympathy = remember {
                    sympathyItems.find { moodItem ->
                        moodItem.postData == feed?.selectedEmotionCode
                    }
                }

                feed?.let {
                    Feed(
                        feedItem = it,
                        onFeedEvent = onFeedEvent,
                        selectedSympathy = selectedSympathy,
                        sympathyItems = sympathyItems,
                        onSympathyChange = { selectedSympathy = it }
                    )
                }
            }

            // 플로팅 버튼이 피드를 가리지 않도록 하기 위함
            item { 
                Spacer(
                    modifier = Modifier
                        .height(JustSayItTheme.Spacing.spaceExtraExtraLarge)
                        .fillMaxWidth()
                        .background(JustSayItTheme.Colors.mainBackground)
                )
            }
        }
    }

    if (uiState.isLoading) {
        CenteredCircularProgress()
    }
}

@Composable
private fun Feed(
    feedItem: EntireFeedEntity,
    onFeedEvent: (FeedEvent) -> Unit,
    selectedSympathy: MoodItem?,
    sympathyItems: List<MoodItem>,
    onSympathyChange: (MoodItem?) -> Unit
) {
    var isPopupForOwnerVisible by remember {
        mutableStateOf(false)
    }

    var isPopupForNotOwnerVisible by remember {
        mutableStateOf(false)
    }

    val popupMenuForNotOwner = listOf(
        PopupMenuItem(
            title = stringResource(id = R.string.popup_report),
            drawable = R.drawable.ic_d_20,
            onItemClick = {
                val reportId = feedItem.storyId
                onFeedEvent(FeedEvent.TargetIdChanged(reportId))
                onFeedEvent(FeedEvent.ReportDialogVisibilityChanged(true))
            },
            postData = null,
            contentColor = null
        ),
        PopupMenuItem(
            title = stringResource(id = R.string.popup_block),
            drawable = R.drawable.ic_block_20,
            onItemClick = {
                val userId = feedItem.writerId
                onFeedEvent(FeedEvent.TargetIdChanged(userId))
                onFeedEvent(FeedEvent.BlockDialogVisibilityChanged(true))
            },
            postData = null,
            contentColor = null
        ),
    )

    val popupMenuForOwner = listOf(
        PopupMenuItem(
            title = stringResource(id = R.string.popup_edit),
            drawable = R.drawable.ic_d_20,
            onItemClick = {
                val feedId = feedItem.storyId
                onFeedEvent(FeedEvent.TargetIdChanged(feedId))
                // TODO 수정 화면으로 이동
            },
            postData = null,
            contentColor = null
        ),
        PopupMenuItem(
            title = stringResource(id = R.string.popup_delete),
            drawable = R.drawable.ic_block_20,
            onItemClick = {
                val feedId = feedItem.storyId
                onFeedEvent(FeedEvent.TargetIdChanged(feedId))
                onFeedEvent(FeedEvent.DeleteDialogVisibilityChanged(true))
            },
            postData = null,
            contentColor = null
        ),
    )

    Feed(
        feedItem = feedItem,
        selectedSympathy = selectedSympathy,
        sympathyItems = sympathyItems,
        onSympathyItemClick = { moodItem ->
            // TODO 데이터베이스 변경 쿼리 - usecase 필요(changeSelectedMoodUseCase)
            onSympathyChange(moodItem)
        },
        onMenuClick = {
            if (feedItem.isOwner == true) {
                isPopupForOwnerVisible = !isPopupForOwnerVisible
            } else if (feedItem.isOwner == false) {
                isPopupForNotOwnerVisible = !isPopupForNotOwnerVisible
            }
        },
        onFeedClick = {
            // 현재로서는 추가적인 depth 없음
        },
        popupMenuForOwner = popupMenuForOwner,
        popupMenuForNotOwner = popupMenuForNotOwner,
        isMenuForOwnerVisible = isPopupForOwnerVisible,
        isMenuForNotOwnerVisible = isPopupForNotOwnerVisible,
        onMenuDismiss = {
            isPopupForOwnerVisible = false
            isPopupForNotOwnerVisible = false
        },
        onMenuItemClick = { popupMenu ->
            isPopupForOwnerVisible = false
            isPopupForNotOwnerVisible = false
            popupMenu.onItemClick?.let { it() }
        }
    )
}

@Composable
private fun AnimatedAppBar(
    lazyListState: LazyListState,
    feedListState: FeedListState,
    onFeedEvent: (FeedEvent) -> Unit
) {
    AnimatedVisibility(
        visible = lazyListState.isScrollingUp(),
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        AppBarFeed(
            lazyListState = lazyListState,
            currentDropdownItem = feedListState.currentEmotion,
            dropdownItems = feedListState.emotionItems,
            isDropdownExpanded = feedListState.isDropdownExpanded,
            onDropdownHeaderClick = { isOpen ->
                onFeedEvent(FeedEvent.DropdownExpandChanged(isOpen))
            },
            onDropdownMenuChange = { updatedMenuItem ->
                onFeedEvent(FeedEvent.EmotionChanged(updatedMenuItem))
            },
            tabItems = feedListState.tabItems,
            selectedTabItem = feedListState.selectedTabItem,
            selectedTabItemColor = JustSayItTheme.Colors.mainTypo,
            unselectedTabItemColor = Gray500,
            onSelectedTabItemChange = { updatedTabItem ->
                onFeedEvent(FeedEvent.SortChanged(updatedTabItem))
            },
        )
    }
}

@Composable
fun getValidatedSympathyItems(feed: EntireFeedEntity?): List<MoodItem> {
    val allItems = rememberMoodItemsForFeed(
        happyCount = feed?.happinessCount,
        sadCount = feed?.sadnessCount,
        surprisedCount = feed?.surprisedCount,
        angryCount = feed?.angryCount
    ).toMutableList()

    if (feed?.isHappinessSelected == false) {
        allItems.remove(
            allItems.find { it.postData == MOOD_HAPPY }
        )
    }

    if (feed?.isSadnessSelected == false) {
        allItems.remove(
            allItems.find { it.postData == MOOD_SAD }
        )
    }

    if (feed?.isSurprisedSelected == false) {
        allItems.remove(
            allItems.find { it.postData == MOOD_SURPRISED }
        )
    }

    if (feed?.isAngrySelected == false) {
        allItems.remove(
            allItems.find { it.postData == MOOD_ANGRY }
        )
    }

    return allItems
}