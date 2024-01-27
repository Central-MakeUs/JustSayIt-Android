package com.sowhat.report_presentation.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.DropdownItem
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.common.rememberNestedScrollViewState
import com.sowhat.designsystem.component.AppBarMyPage
import com.sowhat.designsystem.component.VerticalNestedScrollView
import com.sowhat.designsystem.theme.Black
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.report_presentation.component.MyFeed
import com.sowhat.report_presentation.component.RailBackground
import com.sowhat.report_presentation.component.Report

@Composable
fun MyPageRoute(
    navController: NavController
) {
    MyPageScreen()
}

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        val scope = rememberCoroutineScope()
        val nestedScrollViewState = rememberNestedScrollViewState()
        VerticalNestedScrollView(
            modifier = Modifier.padding(paddingValues),
            state = nestedScrollViewState,
            header = {
                Report(Modifier,"케이엠", true)
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val lazyListState = rememberLazyListState()
                val isItemIconVisible = remember {
                    derivedStateOf { lazyListState.firstVisibleItemScrollOffset <= 16 }
                }
                var currentState by remember {
                    mutableStateOf(
                        MoodItem(drawable = R.drawable.ic_happy_96, postData = "HAPPY",
                            title = "행복", selectedTextColor = Color.White,
                            unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
                    )
                }

                val isScrollInProgress = lazyListState.isScrollInProgress

                var currentDate by remember {
                    mutableStateOf(
                        "24.01.27"
                    )
                }

                AppBarMyPage(
                    currentDropdownItem = DropdownItem("전체"),
                    dropdownItems = listOf(),
                    isDropdownExpanded = false,
                    onDropdownHeaderClick = {},
                    onDropdownMenuChange = {},
                    tabItems = listOf("최신순", "오래된순"),
                    selectedTabItem = "최신순",
                    selectedTabItemColor = JustSayItTheme.Colors.mainTypo,
                    unselectedTabItemColor = JustSayItTheme.Colors.inactiveTypo,
                    onSelectedTabItemChange = {}
                )

                RailBackground(
                    lazyListState = lazyListState,
                    currentMood = currentState,
                    currentDate = currentDate,
                    isScrollInProgress = isScrollInProgress
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = JustSayItTheme.Spacing.spaceSm
                            ),
                        state = lazyListState,
                        contentPadding = PaddingValues(vertical = JustSayItTheme.Spacing.spaceBase)
                    ) {
                        items(count = 20) { index ->
                            val item = when {
                                index % 4 == 0 ->  MoodItem(drawable = R.drawable.ic_happy_96, postData = "HAPPY",
                                    title = "행복", selectedTextColor = Color.White,
                                    unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
                                index % 4 == 1 ->  MoodItem(drawable = R.drawable.ic_sad_96, postData = "HAPPY",
                                    title = "행복", selectedTextColor = Color.White,
                                    unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
                                index % 4 == 2 ->  MoodItem(drawable = R.drawable.ic_angry_96, postData = "HAPPY",
                                    title = "행복", selectedTextColor = Color.White,
                                    unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
                                else ->  MoodItem(drawable = R.drawable.ic_surprise_96, postData = "HAPPY",
                                    title = "행복", selectedTextColor = Color.White,
                                    unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
                            }

                            if (remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value == index) {
                                currentState = item
                            }

                            MyFeed(
                                isPrivate = true,
                                mood = item,
                                isStatusVisible = if (remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value == index) isItemIconVisible.value else true,
                                text = "ok\nok",
                                images = emptyList(),
                                onMenuClick = {},
                                date = "22.11.23",
                                isScrollInProgress = isScrollInProgress
                            )

                            Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceBase))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen()
}