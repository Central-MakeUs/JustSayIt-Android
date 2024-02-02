package com.sowhat.designsystem.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.sowhat.designsystem.R
import com.sowhat.designsystem.theme.JustSayItTheme

data class MoodItem(
    val drawable: Int,
    val title: String,
    val postData: String, // 서버에 실질적으로 보낼 데이터
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val unselectedBackgroundColor: Color,
    val selectedBackgroundColor: Color,
)

@Composable
fun rememberMoodItems(): List<MoodItem> {
    val moodItems = listOf(
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_happy_96,
            title = "행복",
            postData = MOOD_HAPPY,
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_sad_96,
            title = "슬픔",
            postData = MOOD_SAD,
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_surprise_96,
            title = "놀람",
            postData = MOOD_SURPRISED,
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = com.sowhat.designsystem.R.drawable.ic_angry_96,
            title = "화남",
            postData = MOOD_ANGRY,
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
    )

    return remember { moodItems }
}

@Composable
fun rememberMoodItemsForFeed(
    happyCount: Long?,
    sadCount: Long?,
    surprisedCount: Long?,
    angryCount: Long?
): List<MoodItem> {
    val moodItems = listOf(
        MoodItem(
            drawable = R.drawable.ic_happy_96,
            title = (happyCount ?: 0).toString(),
            postData = MOOD_HAPPY,
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.happy,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = R.drawable.ic_sad_96,
            title = (sadCount ?: 0).toString(),
            postData = MOOD_SAD,
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.sad,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = R.drawable.ic_surprise_96,
            title = (surprisedCount ?: 0).toString(),
            postData = MOOD_SURPRISED,
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.surprise,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        ),
        MoodItem(
            drawable = R.drawable.ic_angry_96,
            title = (angryCount ?: 0).toString(),
            postData = MOOD_ANGRY,
            selectedTextColor = JustSayItTheme.Colors.mainBackground,
            unselectedTextColor = JustSayItTheme.Colors.mainTypo,
            selectedBackgroundColor = JustSayItTheme.Colors.angry,
            unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground
        )
    )

    return remember {
        moodItems
    }
}