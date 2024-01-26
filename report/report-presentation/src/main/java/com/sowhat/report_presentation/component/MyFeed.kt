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
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.common.noRippleClickable
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.component.TimelineFeedImages
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun MyFeed(
    modifier: Modifier = Modifier,
    isPrivate: Boolean,
    mood: MoodItem,
    isStatusVisible: Boolean,
    onMenuClick: () -> Unit,
    text: String,
    images: List<String>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceXS)
    ) {
        MoodStatus(
            mood = mood,
            isStatusVisible = isStatusVisible
        )

        FeedCard(isPrivate, onMenuClick, text, images)
    }
}

@Composable
private fun FeedCard(
    isPrivate: Boolean,
    onMenuClick: () -> Unit,
    text: String,
    images: List<String>
) {
    Card(
        modifier = Modifier
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
private fun PrivateStatus(isPrivate: Boolean) {
    if (isPrivate) {
        Icon(
            painter = painterResource(id = R.drawable.ic_lock_16),
            contentDescription = "locked",
            tint = JustSayItTheme.Colors.inactiveTypo
        )
    } else {
        Icon(
            painter = painterResource(id = R.drawable.ic_unlock_16),
            contentDescription = "unlocked",
            tint = JustSayItTheme.Colors.inactiveTypo
        )
    }
}

@Composable
fun MoodStatus(
    modifier: Modifier = Modifier,
    mood: MoodItem,
    isStatusVisible: Boolean
) {
    Box(
        modifier = modifier
            .width(24.dp)
            .padding(top = JustSayItTheme.Spacing.spaceBase),
        contentAlignment = Alignment.Center
    ) {
        if (isStatusVisible) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .aspectRatio(1f),
                painter = painterResource(id = mood.drawable),
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
                mood = MoodItem(drawable = com.sowhat.designsystem.R.drawable.ic_happy_96, postData = "HAPPY",
                    title = "행복", selectedTextColor = Color.White,
                    unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White),
                isStatusVisible = true,
                text = "ok\nok",
                images = emptyList(),
                onMenuClick = {}
            )
            
            Spacer(modifier = Modifier.height(JustSayItTheme.Spacing.spaceBase))
        }

        item {
            MyFeed(
                isPrivate = true,
                mood = MoodItem(drawable = com.sowhat.designsystem.R.drawable.ic_happy_96, postData = "HAPPY",
                    title = "행복", selectedTextColor = Color.White,
                    unselectedTextColor = Color.White, unselectedBackgroundColor = Color.White, selectedBackgroundColor = Color.White),
                isStatusVisible = true,
                text = "ok\nok",
                images = listOf("", "", ""),
                onMenuClick = {}
            )
        }
    }
}