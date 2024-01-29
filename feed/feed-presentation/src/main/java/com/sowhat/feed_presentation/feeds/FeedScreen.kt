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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.component.AppBarFeed
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.feed_presentation.common.FeedAppBarEvent
import com.sowhat.feed_presentation.common.FeedAppBarState
import com.sowhat.designsystem.common.isScrollingUp
import com.sowhat.feed_presentation.component.Feed

@Composable
fun FeedRoute(
    navController: NavController,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val appBarState = viewModel.appBarState

    FeedScreen(
        navController = navController,
        appBarState = appBarState,
        onAppBarEvent = viewModel::onAppBarEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    appBarState: FeedAppBarState,
    onAppBarEvent: (FeedAppBarEvent) -> Unit,
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
                    currentDropdownItem = appBarState.currentEmotion,
                    dropdownItems = appBarState.emotionItems,
                    isDropdownExpanded = appBarState.isDropdownExpanded,
                    onDropdownHeaderClick = { isOpen ->
                        onAppBarEvent(FeedAppBarEvent.DropdownExpandChanged(isOpen))
                    },
                    onDropdownMenuChange = { updatedMenuItem ->
                        onAppBarEvent(FeedAppBarEvent.EmotionChanged(updatedMenuItem))
                    },
                    tabItems = appBarState.tabItems,
                    selectedTabItem = appBarState.selectedTabItem,
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
                .padding(paddingValues),
            state = lazyListState
        ){
            // TODO 하드코딩된 것 지우기
            item {
                DummyData(0)
                DummyData(1)
                DummyData(2)
                DummyData(3)
                DummyData(4)
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
private fun DummyData(count: Int) {
    val moodItems = listOf(
        MoodItem(
            drawable = R.drawable.ic_happy_96,
            title = "행복",
            postData = "HAPPY",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.happy,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = R.drawable.ic_sad_96,
            title = "슬픔",
            postData = "SAD",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.sad,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = R.drawable.ic_surprise_96,
            title = "놀람",
            postData = "SURPRISED",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.surprise,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = R.drawable.ic_angry_96,
            title = "화남",
            postData = "ANGRY",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.angry,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
    )

    var selectedMood by remember {
        mutableStateOf<MoodItem?>(null)
    }

    Feed(
        profileUrl = "https://github.com/kmkim2689/Android-Wiki/assets/101035437/e310b0cf-f931-4afe-98b1-8d7b88900a0f",
        nickname = "케이엠",
        date = "2024-01-18",
        text = "안녕하세요 피드 미리보기 테스트입니다. 안녕하세요 피드 미리보기 테ㅅ트입니다. 안녕하세요 피드 미리보기 테스트입니다.",
        images = when(count) {
            0 -> emptyList()
            1 -> listOf(
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
            )
            2 -> listOf(
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/6fbf0375-5299-4d1c-a95e-6a04bad00eac",
            )
            3 -> listOf(
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/6fbf0375-5299-4d1c-a95e-6a04bad00eac",
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/15d4c57c-67d9-4c31-aad0-883d769025ca"
            )
            else -> listOf(
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/0572b856-8439-43a1-b9f0-79897a29ae60",
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/6fbf0375-5299-4d1c-a95e-6a04bad00eac",
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/15d4c57c-67d9-4c31-aad0-883d769025ca",
                "https://github.com/kmkim2689/Android-Wiki/assets/101035437/15d4c57c-67d9-4c31-aad0-883d769025ca"
            )
        },
        selectedSympathy = selectedMood,
        sympathyItems = moodItems,
        onSympathyItemClick = { selectedMood = it },
        onMenuClick = {},
        onFeedClick = {}
    )

}

@Preview
@Composable
fun FeedScreenPreview() {
    val navController = rememberNavController()
    var appBarState by remember {
        mutableStateOf(FeedAppBarState())
    }
    FeedScreen(
        navController = navController,
        appBarState = appBarState,
        onAppBarEvent = {
            when (it) {
                is FeedAppBarEvent.EmotionChanged -> {
                    appBarState = appBarState.copy(
                        currentEmotion = it.mood
                    )
                }
                is FeedAppBarEvent.SortChanged -> {
                    appBarState = appBarState.copy(
                        selectedTabItem = it.sortBy
                    )
                }
                is FeedAppBarEvent.DropdownExpandChanged -> {
                    appBarState = appBarState.copy(
                        isDropdownExpanded = it.isOpen
                    )
                }
            }
        }
    )

}