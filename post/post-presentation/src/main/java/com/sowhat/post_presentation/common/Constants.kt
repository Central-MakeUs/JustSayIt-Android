package com.sowhat.post_presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.post_presentation.R

@Composable
fun rememberMoodItems(): List<MoodItem> {
    val moodItems = listOf(
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_happy_96,
            title = "행복",
            postData = "EMOTION001",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_sad_96,
            title = "슬픔",
            postData = "EMOTION002",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_surprise_96,
            title = "놀람",
            postData = "EMOTION003",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_angry_96,
            title = "화남",
            postData = "EMOTION004",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
    )

    return remember { moodItems }
}