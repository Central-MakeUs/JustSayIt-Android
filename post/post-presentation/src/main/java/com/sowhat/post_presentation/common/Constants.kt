package com.sowhat.post_presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.post_presentation.R

@Composable
fun rememberMoodItems(): List<MoodItem> {
    // TODO : post data 확인 필요
    val moodItems = listOf(
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_happy_24,
            title = "행복",
            postData = "HAPPY",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_sad_24,
            title = "슬픔",
            postData = "SAD",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_surprise_24,
            title = "놀람",
            postData = "SURPRISED",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_angry_24,
            title = "화남",
            postData = "ANGRY",
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
    )

    return remember { moodItems }
}