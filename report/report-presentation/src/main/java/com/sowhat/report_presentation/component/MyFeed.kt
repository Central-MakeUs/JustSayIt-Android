package com.sowhat.report_presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.noRippleClickable
import com.sowhat.designsystem.component.TimelineFeedImages
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun MyFeed(
    modifier: Modifier = Modifier,
    isPrivate: Boolean,
    currentDate: String?,
    mood: Mood?,
    date: String,
    isMoodVisible: Boolean,
    onMenuClick: () -> Unit,
    text: String,
    images: List<String>,
    isScrollInProgress: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
    ) {

        if (isMoodVisible && isScrollInProgress) {
            DateBadge(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .zIndex(2f),
                date = date,
                currentDate = currentDate
            )
        }

//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .background(color = Color.Transparent),
//            horizontalArrangement = Arrangement
//                .spacedBy(JustSayItTheme.Spacing.spaceXS)
//        ) {
//            MoodStatus(
//                mood = mood,
//                isStatusVisible = isStatusVisible
//            )
//
//        }
        FeedCard(
            modifier = Modifier.padding(start = 36.dp),
            isPrivate = isPrivate,
            onMenuClick = onMenuClick,
            text = text,
            images = images
        )
    }
}

@Composable
private fun FeedCard(
    modifier: Modifier = Modifier,
    isPrivate: Boolean,
    onMenuClick: () -> Unit,
    text: String,
    images: List<String>
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = JustSayItTheme.Shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = JustSayItTheme.Colors.mainBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        FeedContent(isPrivate, onMenuClick, text, images)
    }
}

@Composable
private fun FeedContent(
    isPrivate: Boolean,
    onMenuClick: () -> Unit,
    text: String,
    images: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = JustSayItTheme.Spacing.spaceBase),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = JustSayItTheme.Spacing.spaceBase),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrivateStatus(isPrivate)
            MenuButton(onMenuClick)
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JustSayItTheme.Spacing.spaceBase),
            text = text,
            style = JustSayItTheme.Typography.body1,
            color = JustSayItTheme.Colors.mainTypo
        )

        FeedImages(images)
    }
}

@Composable
private fun FeedImages(images: List<String>) {
    if (images.isNotEmpty()) {
        TimelineFeedImages(
            modifier = Modifier.padding(
                top = JustSayItTheme.Spacing.spaceBase,
                start = JustSayItTheme.Spacing.spaceBase,
                end = JustSayItTheme.Spacing.spaceBase,
            ),
            models = images
        )
    }
}

@Composable
private fun MenuButton(onMenuClick: () -> Unit) {
    Box(
        modifier = Modifier
            .noRippleClickable { onMenuClick() }
    ) {
        Icon(
            modifier = Modifier.padding(JustSayItTheme.Spacing.spaceBase),
            painter = painterResource(id = R.drawable.ic_more_20),
            contentDescription = "more",
            tint = JustSayItTheme.Colors.mainTypo
        )
    }
}

@Composable
private fun PrivateStatus(isOpen: Boolean) {
    OpenStatusBadge(isOpen = isOpen)
}

@Composable
fun MoodStatus(
    modifier: Modifier = Modifier,
    mood: Mood?,
    isStatusVisible: Boolean
) {
    Box(
        modifier = modifier
            .width(24.dp)
            .padding(top = JustSayItTheme.Spacing.spaceBase),
        contentAlignment = Alignment.Center
    ) {
        if (isStatusVisible && mood != null) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .aspectRatio(1f),
                painter = painterResource(id = mood.drawable!!),
                contentDescription = "mood_icon"
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun MyFeedPreview() {
    LazyColumn() {
        item {
            MyFeed(
                isPrivate = true,
                mood = Mood.HAPPY,
                isMoodVisible = true,
                text = "ok\nok",
                images = emptyList(),
                onMenuClick = {},
                date = "22.11.22",
                isScrollInProgress = true,
                currentDate = "22.11.22"
            )
            
            Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceBase))
        }

        item {
            MyFeed(
                isPrivate = true,
                mood = Mood.HAPPY,
                isMoodVisible = true,
                text = "ok\nok",
                images = listOf("", "", ""),
                onMenuClick = {},
                date = "22.11.23",
                isScrollInProgress = true,
                currentDate = "22.11.23"
            )
        }
    }
}