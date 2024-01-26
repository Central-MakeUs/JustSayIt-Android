package com.sowhat.report_presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun RailBackground(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    currentMood: MoodItem,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground),
    ) {
        Rail()

        MoodStatus(
            modifier = Modifier
                .padding(
                    start = JustSayItTheme.Spacing.spaceSm,
                    top = JustSayItTheme.Spacing.spaceBase
                ),
            mood = currentMood,
            isStatusVisible = true
        )

        content()
    }
}

@Composable
fun Rail(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = JustSayItTheme.Spacing.spaceSm)
            .width(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(JustSayItTheme.Spacing.spaceXS),
            color = Gray200
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun RailBackgroundPreview() {
    val lazyListState = rememberLazyListState()
    val isItemIconVisible = remember {
        derivedStateOf { lazyListState.firstVisibleItemScrollOffset <= 16 }
    }
    var currentState by remember {
        mutableStateOf(
            MoodItem(drawable = com.sowhat.designsystem.R.drawable.ic_happy_96, postData = "HAPPY",
                title = "행복", selectedTextColor = Color.White,
                unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
        )
    }

    RailBackground(
        lazyListState = lazyListState,
        currentMood = currentState
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
                    index % 4 == 0 ->  MoodItem(drawable = com.sowhat.designsystem.R.drawable.ic_happy_96, postData = "HAPPY",
                        title = "행복", selectedTextColor = Color.White,
                        unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
                    index % 4 == 1 ->  MoodItem(drawable = com.sowhat.designsystem.R.drawable.ic_sad_96, postData = "HAPPY",
                        title = "행복", selectedTextColor = Color.White,
                        unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
                    index % 4 == 2 ->  MoodItem(drawable = com.sowhat.designsystem.R.drawable.ic_angry_96, postData = "HAPPY",
                        title = "행복", selectedTextColor = Color.White,
                        unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White)
                    else ->  MoodItem(drawable = com.sowhat.designsystem.R.drawable.ic_surprise_96, postData = "HAPPY",
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
                    onMenuClick = {}
                )

                Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceBase))
            }
        }
    }
}