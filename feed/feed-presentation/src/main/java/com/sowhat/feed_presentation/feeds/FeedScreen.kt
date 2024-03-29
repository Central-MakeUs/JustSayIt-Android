package com.sowhat.feed_presentation.feeds

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sowhat.database.entity.EntireFeedEntity
import com.sowhat.common.model.UiState
import com.sowhat.common.util.ObserveEvents
import com.sowhat.common.util.rememberLazyListState
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
import com.sowhat.designsystem.common.rememberMoodItemsForFeed
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.isScrollingUp
import com.sowhat.designsystem.component.AlertDialogReverse
import com.sowhat.designsystem.component.AppendingCircularProgress
import com.sowhat.designsystem.component.ImageDialog
import com.sowhat.designsystem.component.PopupMenuItem
import com.sowhat.designsystem.component.SelectionAlertDialog
import com.sowhat.feed_presentation.common.PostResult
import com.sowhat.feed_presentation.component.Feed
import com.sowhat.feed_presentation.navigation.navigateToEditScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun FeedRoute(
    navController: NavController,
    viewModel: FeedViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    isPosted: StateFlow<Boolean>?
) {
    // TODO You probably need to do collectAsLazyPagingItems() before declaring your NavHost to save the scroll location
    // https://stackoverflow.com/questions/66744977/save-and-retain-lazycolumn-scroll-position-while-using-paging-3

    val feedListState = viewModel.feedListState.collectAsState().value
    val feedPagingData = viewModel.entireFeedData.collectAsLazyPagingItems()
    val uiState = viewModel.uiState.collectAsState().value
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    val blockSuccessMessage = stringResource(id = R.string.snackbar_block_successful)
    val blockFailureMessage = stringResource(id = R.string.snackbar_block_failure)
    val reportSuccessMessage = stringResource(id = R.string.snackbar_report_successful)
    val reportFailureMessage = stringResource(id = R.string.snackbar_report_failure)
    val deleteSuccessMessage = stringResource(id = R.string.snackbar_delete_successful)
    val deleteFailureMessage = stringResource(id = R.string.snackbar_delete_failure)
    val postEmpathyError = stringResource(id = R.string.snackbar_post_empathy_error)
    val postEmpathySuccessful = stringResource(id = R.string.snackbar_post_empathy_successful)
    val cancelEmpathySuccessful = stringResource(id = R.string.snackbar_cancel_empathy_successful)

    LaunchedEffect(key1 = lifecycleOwner.lifecycle.currentState) {
        when (lifecycleOwner.lifecycle.currentState) {
            Lifecycle.State.CREATED -> {
                Log.i("FeedRoute", "FeedRoute: created")
            }
            Lifecycle.State.STARTED -> {
                Log.i("FeedRoute", "FeedRoute: started")
            }
            Lifecycle.State.RESUMED -> {
                Log.i("FeedRoute", "FeedRoute: resumed")
            }
            else -> {
//                Log.i("FeedRoute", "FeedRoute: ${lifecycleOwner.lifecycle.currentState}")
            }
        }
    }

    LaunchedEffect(key1 = isPosted) {
        if (isPosted?.value == true) {
            Log.i("MyPageRoute", "MyPageRoute: posted")
            viewModel.refreshFeeds()
            feedPagingData.refresh()
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<Boolean>("isPosted")
        }
    }

    ObserveEvents(flow = viewModel.empathyEvent) { event ->
        if (event.isSuccessful) {
            if (event.postData == null) {
                scope.launch {
                    snackbarHostState.showSnackbar(cancelEmpathySuccessful)
                }
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar(postEmpathySuccessful)
                }
            }
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(postEmpathyError)
            }
        }
    }

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
//                feedPagingData.refresh()
            } else {
                snackbarHostState.showSnackbar(
                    message = blockFailureMessage,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    ObserveEvents(flow = viewModel.deleteEvent) { isSuccessful ->
        scope.launch {
            if (isSuccessful) {
                snackbarHostState.showSnackbar(
                    message = deleteSuccessMessage,
                    duration = SnackbarDuration.Short
                )
//                feedPagingData.refresh()
            } else {
                snackbarHostState.showSnackbar(
                    message = deleteFailureMessage,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    FeedScreen(
        navController = navController,
        feedListState = feedListState,
        onFeedEvent = viewModel::onFeedEvent,
        feedPagingData = feedPagingData,
        uiState = uiState,
        postEmpathy = viewModel::postEmpathy,
        cancelEmpathy = viewModel::cancelEmpathy,
        showSnackbar = { text ->
            scope.launch {
                snackbarHostState.showSnackbar(message = text, duration = SnackbarDuration.Short)
            }
        },
        empathyEvent = viewModel.empathyEvent,
    )

    if (feedListState.isImageDialogVisible && feedListState.imageUrl != null) {
        ImageDialog(
            onDismiss = {
                viewModel.onFeedEvent(FeedEvent.ImageDialogVisibilityChanged(false))
                viewModel.onFeedEvent(FeedEvent.ImageUrlChanged(null))
            },
            imageUrl = feedListState.imageUrl
        )
    }

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
        AlertDialogReverse(
            title = stringResource(id = R.string.dialog_title_delete_feed),
            subTitle = stringResource(id = R.string.dialog_subtitle_delete_feed),
            buttonContent = stringResource(
                id = R.string.dialog_button_cancel_delete
            ) to stringResource(id = R.string.dialog_button_delete_feed),
            onAccept = {
                viewModel.onFeedEvent(FeedEvent.DeleteDialogVisibilityChanged(false))
                feedListState.targetId?.let {
                    viewModel.deleteFeed(feedListState.targetId)
                    viewModel.onFeedEvent(FeedEvent.TargetIdChanged(null))
                }
            },
            onDismiss = {
                viewModel.onFeedEvent(FeedEvent.DeleteDialogVisibilityChanged(false))
                viewModel.onFeedEvent(FeedEvent.TargetIdChanged(null))
            }
        )
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
    feedPagingData: LazyPagingItems<EntireFeedEntity>,
    postEmpathy: (Long, String, Int) -> Unit,
    cancelEmpathy: (Long, String?, Int) -> Unit,
    showSnackbar: (String) -> Unit,
    empathyEvent: Flow<PostResult>,
) {
    TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyListState = feedPagingData.rememberLazyListState()
    val scope = rememberCoroutineScope()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground),
        topBar = { AnimatedAppBar(lazyListState, feedListState, onFeedEvent) },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(JustSayItTheme.Colors.mainBackground)
        )

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                feedPagingData.refresh()
            },
//            enter = fadeIn(animationSpec = TweenSpec(durationMillis = 1000)),
//            exit = fadeOut(animationSpec = TweenSpec(durationMillis = 1000))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(JustSayItTheme.Colors.mainBackground)
                    .padding(paddingValues),
                state = lazyListState
            ) {
                items(
                    count = feedPagingData.itemCount,
                    key = feedPagingData.itemKey { item -> item.storyUUID },
                    contentType = { "Feed" }
                ) { index ->
                    val feed = feedPagingData[index]

                    if (feed != null) {
                        var happinessCount by remember { mutableStateOf(feed.happinessCount) }
                        var sadnessCount by remember { mutableStateOf(feed.sadnessCount) }
                        var surprisedCount by remember { mutableStateOf(feed.surprisedCount) }
                        var angryCount by remember { mutableStateOf(feed.angryCount) }

                        val sympathyItems = getValidatedSympathyItems(
                            feed = feed,
                            happinessCount = feed.happinessCount,
                            sadnessCount = feed.sadnessCount,
                            surprisedCount = feed.surprisedCount,
                            angryCount = feed.angryCount
                        )

                        var selectedSympathy by remember {
                            mutableStateOf(
                                sympathyItems.find { moodItem ->
                                    moodItem.postData == feed.selectedEmotionCode
                                }
                            )
                        }

                        Feed(
                            navController = navController,
                            feedItem = feed,
                            onFeedEvent = onFeedEvent,
                            selectedSympathy = selectedSympathy,
                            sympathyItems = sympathyItems,
                            onSympathyChange = { changedItem ->
                                if (changedItem != null) {
                                    selectedSympathy = changedItem
                                    postEmpathy(feed.storyId, changedItem.postData, index)
                                    when (changedItem.postData) {
                                        MOOD_HAPPY -> {
                                            happinessCount = (happinessCount ?: 0) + 1
                                            selectedSympathy?.title = happinessCount.toString()
                                            Log.i(
                                                "FeedScreen",
                                                "happy count changed: $happinessCount"
                                            )
                                        }

                                        MOOD_SAD -> {
                                            sadnessCount = (sadnessCount ?: 0) + 1
                                            selectedSympathy?.title = sadnessCount.toString()
                                        }

                                        MOOD_ANGRY -> {
                                            angryCount = (angryCount ?: 0) + 1
                                            selectedSympathy?.title = angryCount.toString()
                                        }

                                        MOOD_SURPRISED -> {
                                            surprisedCount = (surprisedCount ?: 0) + 1
                                            selectedSympathy?.title = surprisedCount.toString()
                                        }

                                        else -> {}
                                    }
                                } else {
                                    cancelEmpathy(feed.storyId, selectedSympathy?.postData, index)
                                    when (selectedSympathy?.postData) {
                                        MOOD_HAPPY -> {
                                            happinessCount = happinessCount?.minus(1)
                                            Log.i(
                                                "FeedScreen",
                                                "happy count changed: $happinessCount"
                                            )
                                            selectedSympathy?.title = happinessCount.toString()
                                        }

                                        MOOD_SAD -> {
                                            sadnessCount = sadnessCount?.minus(1)
                                            selectedSympathy?.title = sadnessCount.toString()
                                        }

                                        MOOD_ANGRY -> {
                                            angryCount = angryCount?.minus(1)
                                            selectedSympathy?.title = angryCount.toString()
                                        }

                                        MOOD_SURPRISED -> {
                                            surprisedCount = surprisedCount?.minus(1)
                                            selectedSympathy?.title = surprisedCount.toString()
                                        }

                                        else -> {}
                                    }
                                    selectedSympathy = changedItem
                                }
                            },
                            showSnackbar = showSnackbar
                        )
                    }
                }

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

        if (feedPagingData.loadState.refresh is LoadState.Loading) {
            CenteredCircularProgress()
        }
    }
}

@Composable
private fun Feed(
    navController: NavController,
    feedItem: EntireFeedEntity,
    onFeedEvent: (FeedEvent) -> Unit,
    selectedSympathy: MoodItem?,
    sympathyItems: List<MoodItem>,
    onSympathyChange: (MoodItem?) -> Unit,
    showSnackbar: (String) -> Unit
) {
    val postMyFeedErrorMessage = stringResource(id = R.string.snackbar_my_feed_post_empathy)

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
            contentColor = JustSayItTheme.Colors.mainTypo,
            drawableTint = JustSayItTheme.Colors.error
        ),
        PopupMenuItem(
            title = stringResource(id = R.string.popup_report_user),
            drawable = R.drawable.ic_d_20,
            onItemClick = {
                val reportId = feedItem.storyId
                onFeedEvent(FeedEvent.TargetIdChanged(reportId))
                onFeedEvent(FeedEvent.ReportDialogVisibilityChanged(true))
            },
            contentColor = JustSayItTheme.Colors.mainTypo,
            drawableTint = JustSayItTheme.Colors.error
        ),
        PopupMenuItem(
            title = stringResource(id = R.string.popup_block),
            drawable = R.drawable.ic_block_20,
            onItemClick = {
                val userId = feedItem.writerId
                onFeedEvent(FeedEvent.TargetIdChanged(userId))
                onFeedEvent(FeedEvent.BlockDialogVisibilityChanged(true))
            },
            contentColor = JustSayItTheme.Colors.mainTypo,
            drawableTint = JustSayItTheme.Colors.error
        ),
    )

    val popupMenuForOwner = listOf(
        PopupMenuItem(
            title = stringResource(id = R.string.popup_edit),
            drawable = R.drawable.ic_edit_20,
            onItemClick = {
                val feedId = feedItem.storyId
                onFeedEvent(FeedEvent.TargetIdChanged(feedId))
                navController.navigateToEditScreen(feedId)
            },
            postData = null,
            contentColor = JustSayItTheme.Colors.mainTypo,
            drawableTint = JustSayItTheme.Colors.mainTypo
        ),
        PopupMenuItem(
            title = stringResource(id = R.string.popup_delete),
            drawable = R.drawable.ic_delete_20,
            onItemClick = {
                val feedId = feedItem.storyId
                onFeedEvent(FeedEvent.TargetIdChanged(feedId))
                onFeedEvent(FeedEvent.DeleteDialogVisibilityChanged(true))
            },
            contentColor = JustSayItTheme.Colors.error,
            drawableTint = JustSayItTheme.Colors.error
        ),
    )

    Feed(
        feedItem = feedItem,
        selectedSympathy = selectedSympathy,
        sympathyItems = sympathyItems,
        onSympathyItemClick = { moodItem ->
            if (feedItem.isOwner == true) {
                showSnackbar(postMyFeedErrorMessage)
            } else {
                onSympathyChange(moodItem)
            }
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
        },
        onImageClick = { imageUrl ->
            onFeedEvent(FeedEvent.ImageUrlChanged(imageUrl))
            onFeedEvent(FeedEvent.ImageDialogVisibilityChanged(true))
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
            tabItems = emptyList(),
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
fun getValidatedSympathyItems(
    feed: EntireFeedEntity?,
    happinessCount: Long?,
    sadnessCount: Long?,
    surprisedCount: Long?,
    angryCount: Long?
): List<MoodItem> {
    val allItems = rememberMoodItemsForFeed(
        happyCount = happinessCount,
        sadCount = sadnessCount,
        surprisedCount = surprisedCount,
        angryCount = angryCount
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

    return remember { allItems }
}
