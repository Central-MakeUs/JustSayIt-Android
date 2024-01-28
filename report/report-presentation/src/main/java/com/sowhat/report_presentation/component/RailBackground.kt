package com.sowhat.report_presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun RailBackground(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    currentMood: Mood?,
    currentDate: String?,
    isScrollInProgress: Boolean,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(JustSayItTheme.Colors.mainBackground),
    ) {
        Rail()

        AnimatedVisibility(
            visible = isScrollInProgress,
            enter = fadeIn(initialAlpha = 0.3f),
            exit = fadeOut()
        ) {
            MoodStatus(
                modifier = Modifier
                    .padding(
                        start = JustSayItTheme.Spacing.spaceSm,
                    ),
                mood = currentMood,
                isStatusVisible = true
            )
        }


        AnimatedVisibility(
            modifier = Modifier
                .zIndex(2f),
            visible = isScrollInProgress,
            enter = fadeIn(initialAlpha = 0.3f),
            exit = fadeOut()
        ) {
            DateBadge(
                modifier = Modifier
                    .padding(
                        start = JustSayItTheme.Spacing.spaceSm,
                        top = 48.dp
                    ),
                date = currentDate,
                currentDate = ""
            )
        }

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
            Mood.HAPPY
        )
    }

    var currentDate by remember {
        mutableStateOf(
            "24.01.27"
        )
    }

    RailBackground(
        lazyListState = lazyListState,
        currentMood = currentState,
        currentDate = currentDate,
        isScrollInProgress = lazyListState.isScrollInProgress
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
                    index % 4 == 0 ->  Mood.HAPPY
                    index % 4 == 1 ->  Mood.SAD
                    index % 4 == 2 ->  Mood.SURPRISED
                    else ->  Mood.ANGRY
                }

                if (remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value == index) {
                    currentState = item
                }

                MyFeed(
                    isPrivate = true,
                    mood = item,
                    isMoodVisible = if (remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }.value == index) isItemIconVisible.value else true,
                    text = "ok\nok",
                    images = emptyList(),
                    onMenuClick = {},
                    date = "22.11.23",
                    isScrollInProgress = lazyListState.isScrollInProgress,
                    currentDate = "22.11.23"
                )

                Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceBase))
            }
        }
    }
}