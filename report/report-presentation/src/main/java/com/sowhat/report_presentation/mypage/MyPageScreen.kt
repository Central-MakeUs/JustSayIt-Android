package com.sowhat.report_presentation.mypage

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sowhat.database.entity.MyFeedEntity
import com.sowhat.common.util.ObserveEvents
import com.sowhat.common.util.rememberLazyListState
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.rememberNestedScrollViewState
import com.sowhat.designsystem.component.AppBarMyPage
import com.sowhat.designsystem.component.Chip
import com.sowhat.designsystem.component.PopupMenuItem
import com.sowhat.designsystem.component.VerticalNestedScrollView
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.isScrollingUp
import com.sowhat.designsystem.component.AlertDialogReverse
import com.sowhat.designsystem.component.AppendingCircularProgress
import com.sowhat.designsystem.component.CenteredCircularProgress
import com.sowhat.designsystem.component.ImageDialog
import com.sowhat.report_presentation.common.MyFeedEvent
import com.sowhat.report_presentation.common.MyFeedUiState
import com.sowhat.report_presentation.common.ReportEvent
import com.sowhat.report_presentation.common.ReportUiState
import com.sowhat.report_presentation.common.toDate
import com.sowhat.report_presentation.component.MyFeed
import com.sowhat.report_presentation.component.RailBackground
import com.sowhat.report_presentation.component.Report
import com.sowhat.report_presentation.navigation.navigateToEditScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun MyPageRoute(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    isPosted: StateFlow<Boolean>?
) {
    val context = LocalContext.current
    val myFeedUiState = viewModel.myFeedUiState.collectAsState().value
    val myFeedPagingData = viewModel.myFeedPagingData.collectAsLazyPagingItems()
    val reportUiState = viewModel.reportUiState.collectAsState().value

    val scope = rememberCoroutineScope()

    val deleteSuccessMessage = stringResource(id = R.string.snackbar_delete_successful)
    val deleteFailureMessage = stringResource(id = R.string.snackbar_delete_failure)
    val postMoodFailureMessage = stringResource(id = R.string.snackbar_post_mood_error)

    LaunchedEffect(key1 = isPosted) {
        if (isPosted?.value == true) {
            Log.i("MyPageRoute", "MyPageRoute: posted")
            viewModel.refreshFeeds()
            myFeedPagingData.refresh()
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<Boolean>("isPosted")
        }
    }

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = false
    )

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            myFeedPagingData.refresh()
        }
    ) {
        MyPageScreen(
            pagingData = myFeedPagingData,
            myFeedUiState = myFeedUiState,
            onMyFeedEvent = viewModel::onMyFeedEvent,
            reportUiState = reportUiState,
            onReportEvent = viewModel::onReportEvent,
            onDelete = viewModel::deleteFeed,
            onMoodSubmit = viewModel::postNewMood,
            onEdit = { feedId ->
                navController.navigateToEditScreen(feedId)
            }
        )
    }

    ObserveEvents(flow = viewModel.postNewMoodEvent) { isSuccessful ->
        scope.launch {
            if (isSuccessful) {
                viewModel.getTodayMood()
                return@launch
            } else {
                snackbarHostState.showSnackbar(message = postMoodFailureMessage)
            }
        }
    }

    ObserveEvents(flow = viewModel.deleteEvent) { isSuccessful ->
        scope.launch {
            viewModel.onMyFeedEvent(MyFeedEvent.LoadingChanged(false))
            if (isSuccessful) {
                snackbarHostState.showSnackbar(
                    message = deleteSuccessMessage,
                    duration = SnackbarDuration.Short
                )
            } else {
                snackbarHostState.showSnackbar(
                    message = deleteFailureMessage,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    if (myFeedUiState.isImageDialogVisible) {
        ImageDialog(
            onDismiss = {
                viewModel.onMyFeedEvent(MyFeedEvent.ImageDialogVisibilityChanged(false))
                viewModel.onMyFeedEvent(MyFeedEvent.ImageUrlChanged(null))
            },
            imageUrl = myFeedUiState.imageUrl
        )
    }
    
    if (myFeedUiState.isLoading) {
        CenteredCircularProgress()
    }
}

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    pagingData: LazyPagingItems<MyFeedEntity>,
    myFeedUiState: MyFeedUiState,
    reportUiState: ReportUiState,
    onMyFeedEvent: (MyFeedEvent) -> Unit,
    onReportEvent: (ReportEvent) -> Unit,
    onDelete: (Long) -> Unit,
    onMoodSubmit: (Mood?) -> Unit,
    onEdit: (Long) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        val scope = rememberCoroutineScope()
        val nestedScrollViewState = rememberNestedScrollViewState()

        VerticalNestedScrollView(
            modifier = Modifier
                .padding(paddingValues)
                .background(JustSayItTheme.Colors.mainBackground),
            state = nestedScrollViewState,
            header = {
                Report(
                    reportUiState = reportUiState,
                    nickname = reportUiState.nickname,
                    isActive = reportUiState.isSubmitEnabled,
                    selectedMood = reportUiState.selectedMood,
                    onSelectedMoodChange = { mood ->
                        Log.i("MyPageScreen", "mood changed: $mood")
                        onReportEvent(ReportEvent.SelectedMoodChanged(mood))
                        mood?.let {
                            onReportEvent(ReportEvent.SubmitActiveChanged(true))
                        } ?: onReportEvent(ReportEvent.SubmitActiveChanged(false))
                    },
                    onMoodSubmit = {
                        onMoodSubmit(reportUiState.selectedMood)
                        onReportEvent(ReportEvent.SubmitActiveChanged(false))
                        onReportEvent(ReportEvent.SelectedMoodChanged(null))
                    },
                    todayMoodItems = reportUiState.moodList
                )
            }
        ) {
            MyFeedItemsScreen(
                modifier = Modifier,
                myFeedUiState = myFeedUiState,
                onMyFeedEvent = onMyFeedEvent,
                pagingData = pagingData,
                onEdit = onEdit
            )
        }
    }

    if (myFeedUiState.isDeleteDialogVisible && myFeedUiState.targetId != null) {
        AlertDialogReverse(
            title = stringResource(id = R.string.dialog_title_delete_feed),
            targetId = myFeedUiState.targetId,
            subTitle = stringResource(
                id = R.string.dialog_subtitle_delete_feed
            ),
            buttonContent = stringResource(
                id = R.string.dialog_button_cancel_delete
            ) to stringResource(
                id = R.string.dialog_button_delete_feed
            ),
            onAccept = {
                onMyFeedEvent(MyFeedEvent.LoadingChanged(true))
                onDelete(myFeedUiState.targetId)

                // 삭제가 끝나고 나서 진행할 일
                onMyFeedEvent(MyFeedEvent.FeedDeleteDialogChanged(false))
                onMyFeedEvent(MyFeedEvent.FeedTargetIdChanged(null))
            },
            onDismiss = {
                onMyFeedEvent(MyFeedEvent.FeedDeleteDialogChanged(false))
                onMyFeedEvent(MyFeedEvent.FeedTargetIdChanged(null))
            }
        )
    }
}

@Composable
private fun MyFeedItemsScreen(
    modifier: Modifier = Modifier,
    myFeedUiState: MyFeedUiState,
    onMyFeedEvent: (MyFeedEvent) -> Unit,
    pagingData: LazyPagingItems<MyFeedEntity>,
    onEdit: (Long) -> Unit,
) {
    val lazyListState = pagingData.rememberLazyListState()

    val isItemIconVisible = remember {
        derivedStateOf { lazyListState.firstVisibleItemScrollOffset <= 0 }
    }

    val moodItems = Mood.values().toList()
    var currentState by remember { mutableStateOf<Mood?>(null) }
    var currentDate by remember { mutableStateOf<String?>(null) }

    val isScrollInProgress = lazyListState.isScrollInProgress

    var isCurrentFeedInfoVisible by remember {
        mutableStateOf(isScrollInProgress)
    }

    LaunchedEffect(key1 = isScrollInProgress) {
        isCurrentFeedInfoVisible = if (!isScrollInProgress) {
            delay(5000)
            false
        } else true
    }

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            AppBarMyPage(
                currentDropdownItem = myFeedUiState.emotion,
                dropdownItems = moodItems,
                isDropdownExpanded = myFeedUiState.isDropdownOpen,
                onDropdownHeaderClick = { isOpen ->
                    onMyFeedEvent(MyFeedEvent.DropdownOpenChanged(isOpen))
                },
                onDropdownMenuChange = { mood ->
                    onMyFeedEvent(MyFeedEvent.EmotionChanged(mood))
                },
                tabItems = myFeedUiState.sortByItems,
                selectedTabItem = myFeedUiState.sortBy,
                selectedTabItemColor = JustSayItTheme.Colors.mainTypo,
                unselectedTabItemColor = JustSayItTheme.Colors.inactiveTypo,
                onSelectedTabItemChange = { tabItem ->
                    onMyFeedEvent(MyFeedEvent.SortChanged(tabItem))
                }
            )
        }
    ) { paddingValues ->
        MyFeedList(
            modifier = Modifier.padding(paddingValues),
            lazyListState = lazyListState,
            currentState = currentState,
            currentDate = currentDate,
            isScrollInProgress = isCurrentFeedInfoVisible,
            pagingData = pagingData,
            moodItems = moodItems,
            isItemIconVisible = isItemIconVisible,
            onFirstItemIndexChange = { myFeed ->
                currentState = moodItems.find { it.postData == myFeed.writerEmotion }
                currentDate = myFeed.createdAt.toDate()
            },
            myFeedUiState = myFeedUiState,
            onMyFeedEvent = onMyFeedEvent,
            onEdit = onEdit,
        )
    }
}

@Composable
private fun MyFeedList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    currentState: Mood?,
    currentDate: String?,
    isScrollInProgress: Boolean,
    pagingData: LazyPagingItems<MyFeedEntity>,
    moodItems: List<Mood>,
    isItemIconVisible: State<Boolean>,
    onFirstItemIndexChange: (MyFeedEntity) -> Unit,
    myFeedUiState: MyFeedUiState,
    onMyFeedEvent: (MyFeedEvent) -> Unit,
    onEdit: (Long) -> Unit,
) {
    RailBackground(
        modifier = modifier,
        lazyListState = lazyListState,
        selectedEmotion = myFeedUiState.emotion,
        currentMood = currentState,
        currentDate = currentDate,
        isScrollInProgress = isScrollInProgress
    ) {
        MyFeedListContent(
            lazyListState = lazyListState,
            pagingData = pagingData,
            onFirstItemIndexChange = onFirstItemIndexChange,
            isItemIconVisible = isItemIconVisible,
            onMyFeedEvent = onMyFeedEvent,
            currentDate = currentDate,
            moodItems = moodItems,
            isScrollInProgress = isScrollInProgress,
            myFeedUiState = myFeedUiState,
            onEdit = onEdit,
        )
    }
}

@Composable
private fun MyFeedListContent(
    lazyListState: LazyListState,
    pagingData: LazyPagingItems<MyFeedEntity>,
    onFirstItemIndexChange: (MyFeedEntity) -> Unit,
    isItemIconVisible: State<Boolean>,
    onMyFeedEvent: (MyFeedEvent) -> Unit,
    currentDate: String?,
    moodItems: List<Mood>,
    isScrollInProgress: Boolean,
    myFeedUiState: MyFeedUiState,
    onEdit: (Long) -> Unit,
) {
//    LaunchedEffect(key1 = myFeedUiState.emotion, key2 = myFeedUiState.sortBy) {
//        Log.i("MyPage", "paging data changed")
//        lazyListState.scrollBy(0f)
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = JustSayItTheme.Spacing.spaceSm
            ),
        state = lazyListState,
        contentPadding = PaddingValues(vertical = JustSayItTheme.Spacing.spaceBase)
    ) {
        items(
            count = pagingData.itemCount,
            key = pagingData.itemKey { it.storyId },
            contentType = pagingData.itemContentType { "images" }
        ) { index ->
            val item = pagingData[index]

            item?.let { myFeed ->
                FeedItem(
                    lazyListState = lazyListState,
                    index = index,
                    onFirstItemIndexChange = onFirstItemIndexChange,
                    myFeed = myFeed,
                    isItemIconVisible = isItemIconVisible,
                    item = item,
                    onMyFeedEvent = onMyFeedEvent,
                    currentDate = currentDate,
                    moodItems = moodItems,
                    isScrollInProgress = isScrollInProgress,
                    onEdit = onEdit,
                )
            }

            Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceBase))
        }

        if (pagingData.loadState.refresh == LoadState.Loading) {
            item {
                AppendingCircularProgress(
                    modifier = Modifier
                        .padding(vertical = JustSayItTheme.Spacing.spaceLg)
                )
            }
        }
    }
}

@Composable
private fun FeedItem(
    lazyListState: LazyListState,
    index: Int,
    onFirstItemIndexChange: (MyFeedEntity) -> Unit,
    myFeed: MyFeedEntity,
    isItemIconVisible: State<Boolean>,
    item: MyFeedEntity?,
    onMyFeedEvent: (MyFeedEvent) -> Unit,
    currentDate: String?,
    moodItems: List<Mood>,
    isScrollInProgress: Boolean,
    onEdit: (Long) -> Unit,
) {
    if (remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value == index) {
        onFirstItemIndexChange(myFeed)
    }

    val isFirstItem = remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }.value == index

    val isMoodVisible = if (isFirstItem) isItemIconVisible.value else true
    var isPopupMenuVisible by remember {
        mutableStateOf(false)
    }

    val sympathyMoodItems = getSympathyMoodItems(item)

    val popupMenuItems = listOf(
        PopupMenuItem(
            title = stringResource(id = R.string.popup_edit),
            drawable = R.drawable.ic_edit_20,
            postData = myFeed.storyId,
            contentColor = JustSayItTheme.Colors.mainTypo,
            onItemClick = {
                Log.i("MyPage", "FeedItem: ${myFeed.storyId}")
                onEdit(myFeed.storyId)
            }
        ),
        PopupMenuItem(
            title = stringResource(id = R.string.popup_delete),
            drawable = R.drawable.ic_delete_20,
            postData = myFeed.storyId,
            contentColor = JustSayItTheme.Colors.error,
            onItemClick = {
                onMyFeedEvent(MyFeedEvent.FeedTargetIdChanged(myFeed.storyId))
                onMyFeedEvent(MyFeedEvent.FeedDeleteDialogChanged(true))
            }
        )
    )

    MyFeed(
        currentDate = currentDate,
        isOpen = myFeed.isOpened,
        mood = moodItems.find { it.postData == myFeed.writerEmotion },
        isMoodVisible = isMoodVisible,
        text = myFeed.bodyText,
        images = myFeed.photo,
        onMenuClick = { isPopupMenuVisible = true },
        date = myFeed.createdAt.toDate(),
        isScrollInProgress = isScrollInProgress,
        sympathyMoodItems = sympathyMoodItems,
        isEdited = myFeed.isModified,
        isMenuVisible = isPopupMenuVisible,
        popupMenuItem = popupMenuItems,
        onPopupMenuDismiss = { isPopupMenuVisible = false },
        onMenuItemClick = {
            isPopupMenuVisible = false
            it.onItemClick?.let { it() }
        },
        onImageClick = { imageUrl ->
            onMyFeedEvent(MyFeedEvent.ImageUrlChanged(imageUrl))
            onMyFeedEvent(MyFeedEvent.ImageDialogVisibilityChanged(true))
        }
    )
}

@Composable
private fun getSympathyMoodItems(
    item: MyFeedEntity?
): List<@Composable () -> Unit> {
    val sympathyMoodItems = mutableListOf<@Composable () -> Unit>()
    val drawableSize = JustSayItTheme.Spacing.spaceLg
    val textStyle = JustSayItTheme.Typography.detail1
    val textColor = JustSayItTheme.Colors.mainTypo

    item?.let {
        if (item.isHappinessSelected) {
            sympathyMoodItems.add {
                Chip(
                    drawableStart = Mood.HAPPY.drawable,
                    drawableSize = drawableSize,
                    countText = item.happinessCount.toString(),
                    textStyle = textStyle,
                    textColor = textColor
                )
            }
        }

        if (item.isSadnessSelected) {
            sympathyMoodItems.add {
                Chip(
                    drawableStart = Mood.SAD.drawable,
                    drawableSize = drawableSize,
                    countText = item.sadnessCount.toString(),
                    textStyle = textStyle,
                    textColor = textColor
                )
            }
        }

        if (item.isAngrySelected) {
            sympathyMoodItems.add {
                Chip(
                    drawableStart = Mood.ANGRY.drawable,
                    drawableSize = drawableSize,
                    countText = item.angryCount.toString(),
                    textStyle = textStyle,
                    textColor = textColor
                )
            }
        }

        if (item.isSurprisedSelected) {
            sympathyMoodItems.add {
                Chip(
                    drawableStart = Mood.SURPRISED.drawable,
                    drawableSize = drawableSize,
                    countText = item.surprisedCount.toString(),
                    textStyle = textStyle,
                    textColor = textColor
                )
            }
        }
    }

    return sympathyMoodItems
}