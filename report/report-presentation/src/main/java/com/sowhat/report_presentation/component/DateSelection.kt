package com.sowhat.report_presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.dashedBorder
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.JustSayItTheme
import kotlin.math.absoluteValue

@Composable
fun DateSelection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        CurrentMoodSection()
        SelectionSlider(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun CurrentMoodSection(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(JustSayItTheme.Shapes.medium)
            .dashedBorder(
                width = 5f,
                color = Gray500,
                cornerRadius = JustSayItTheme.Spacing.spaceSm
            ),
        contentAlignment = Alignment.Center,
        content = {}
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectionSlider(
    modifier: Modifier = Modifier
) {
    // TODO API 나오면 고치기
    var pagerCount = 25
    val pagerState = rememberPagerState(
        initialPage = 20 // 끝 페이지부터 시작
    ) {
        pagerCount
    }

    val itemSize = 84.dp

    val configuration = LocalConfiguration.current
    // 64dp는 Card 및 화면 상에서 사용되는 총 horizontal padding
    val screenWidthDp = configuration.screenWidthDp.dp - 64.dp

    // https://medium.com/@domen.lanisnik/exploring-the-official-pager-in-compose-8c2698c49a98
    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        state = pagerState,
        pageSize = PageSize.Fixed(itemSize),
        verticalAlignment = Alignment.Bottom,
        // 현재 아이템 위치를 중앙으로 맞추기 위함
        contentPadding = PaddingValues(
            start = screenWidthDp / 2 - itemSize / 2,
            end = screenWidthDp / 2 - itemSize / 2
        )
    ) {pageIdx ->
        Column(
            modifier = Modifier
                .width(itemSize) // 기본적으로 아이템들이 왼쪽에 치우쳐 있으므로
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - pageIdx) +
                                    pagerState.currentPageOffsetFraction).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .composed {
                        if (pagerState.currentPage == pageIdx) {
                            padding(bottom = JustSayItTheme.Spacing.spaceBase)
                        } else padding(bottom = JustSayItTheme.Spacing.spaceXS)
                    },
                text = "12:00",
                style = JustSayItTheme.Typography.detail1,
                color = Gray500,
            )

            CurrentMood(
                mood = Mood.HAPPY,
                modifier = Modifier.composed {
                    if (pagerState.currentPage == pageIdx) {
                        size(80.dp)
                    } else size(48.dp)
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DailySectionPreview() {
    Column {
        DateSelection(modifier = Modifier.height(126.dp))
    }
}