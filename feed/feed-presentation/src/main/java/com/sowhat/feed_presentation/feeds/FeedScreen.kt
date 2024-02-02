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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.practice.database.entity.EntireFeedEntity
import com.sowhat.designsystem.common.MOOD_ANGRY
import com.sowhat.designsystem.common.MOOD_HAPPY
import com.sowhat.designsystem.common.MOOD_SAD
import com.sowhat.designsystem.common.MOOD_SURPRISED
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.component.AppBarFeed
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.feed_presentation.common.FeedAppBarEvent
import com.sowhat.feed_presentation.common.FeedListState
import com.sowhat.designsystem.common.isScrollingUp
import com.sowhat.designsystem.common.rememberMoodItemsForFeed
import com.sowhat.feed_presentation.component.Feed

@Composable
fun FeedRoute(
    navController: NavController,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val feedListState = viewModel.feedListState.collectAsState().value
    val feedPagingData = viewModel.entireFeedData.collectAsLazyPagingItems()

    FeedScreen(
        navController = navController,
        feedListState = feedListState,
        onAppBarEvent = viewModel::onAppBarEvent,
        feedPagingData = feedPagingData
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    feedListState: FeedListState,
    onAppBarEvent: (FeedAppBarEvent) -> Unit,
    feedPagingData: LazyPagingItems<EntireFeedEntity>
) {
    TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground),
        topBar = {
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
                        onAppBarEvent(FeedAppBarEvent.DropdownExpandChanged(isOpen))
                    },
                    onDropdownMenuChange = { updatedMenuItem ->
                        onAppBarEvent(FeedAppBarEvent.EmotionChanged(updatedMenuItem))
                    },
                    tabItems = feedListState.tabItems,
                    selectedTabItem = feedListState.selectedTabItem,
                    selectedTabItemColor = JustSayItTheme.Colors.mainTypo,
                    unselectedTabItemColor = Gray500,
                    onSelectedTabItemChange = { updatedTabItem ->
                        onAppBarEvent(FeedAppBarEvent.SortChanged(updatedTabItem))
                    },
                )

            }

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
                        profileUrl = it.profileImg,
                        nickname = it.nickname,
                        date = it.createdAt,
                        text = it.bodyText,
                        images = it.photo,
                        selectedSympathy = selectedSympathy,
                        sympathyItems = sympathyItems,
                        onSympathyItemClick = { moodItem ->
                            // TODO 데이터베이스 변경 쿼리 - usecase 필요(changeSelectedMoodUseCase)
                            selectedSympathy = moodItem
                        },
                        onMenuClick = {
                            if (it.isOwner == true) {
                                // TODO 작성자일 때, 띄워줄 팝업메뉴 분기처리
                            } else {

                            }
                        },
                        onFeedClick = {
                            // 현재로서는 추가적인 depth 없음
                        },
                        isOwner = it.isOwner ?: return@let
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
//
//@Composable
//private fun DummyData(count: Int) {
//    val moodItems = rememberMoodItemsForFeed(111, 2, 3, 5)
//
//    var selectedMood by remember {
//        mutableStateOf<MoodItem?>(null)
//    }
//
//    Feed(
//        profileUrl = "https://github.com/kmkim2689/Android-Wiki/assets/101035437/e310b0cf-f931-4afe-98b1-8d7b88900a0f",
//        nickname = "케이엠",
//        date = "2024-01-18",
//        text = "안녕하세요 피드 미리보기 테스트입니다. 안녕하세요 피드 미리보기 테ㅅ트입니다. 안녕하세요 피드 미리보기 테스트입니다.",
//        images = when(count) {
//            0 -> emptyList()
//            1 -> listOf(
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
//            )
//            2 -> listOf(
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/6fbf0375-5299-4d1c-a95e-6a04bad00eac",
//            )
//            3 -> listOf(
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/6fbf0375-5299-4d1c-a95e-6a04bad00eac",
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/15d4c57c-67d9-4c31-aad0-883d769025ca"
//            )
//            else -> listOf(
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/6fbf0375-5299-4d1c-a95e-6a04bad00eac",
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/15d4c57c-67d9-4c31-aad0-883d769025ca",
//                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/15d4c57c-67d9-4c31-aad0-883d769025ca"
//            )
//        },
//        selectedSympathy = selectedMood,
//        sympathyItems = moodItems,
//        onSympathyItemClick = { selectedMood = it },
//        onMenuClick = {},
//        onFeedClick = {}
//    )
//
//}