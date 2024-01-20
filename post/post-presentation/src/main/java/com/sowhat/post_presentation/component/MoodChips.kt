package com.sowhat.post_presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sowhat.designsystem.component.ChipMedium
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.common.MoodItem

@Composable
fun MoodChips(
    modifier: Modifier = Modifier,
    currentMood: String?,
    onChange: (String) -> Unit,
    moodItems: List<MoodItem>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceXXS)
    ) {
        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceBase))
        }

        itemsIndexed(moodItems) { _, moodItem ->
            val isSelected = currentMood == moodItem.title
            ChipMedium(
                moodItem = moodItem,
                isSelected = isSelected,
                onClick = { item ->
                    onChange(item)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceBase))
        }
    }
}

@Composable
fun MoodChips(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    onClick: (String) -> Unit,
    selectedMoods: List<String>,
    moodItems: List<MoodItem>
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .composed {
                if (!isActive) {
                    alpha(alpha = 0.3f)
                } else this
            },
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceXXS)
    ) {
        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceBase))
        }

        itemsIndexed(moodItems) { _, moodItem ->
            val isSelected = moodItem.title in selectedMoods
            ChipMedium(
                moodItem = moodItem,
                isActive = isActive,
                isSelected = isSelected,
                onClick = { item ->
                    onClick(item)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceBase))
        }
    }
}

@Preview
@Composable
fun MoodChipsPreview() {

    val moods = listOf(
        MoodItem(com.sowhat.designsystem.R.drawable.ic_happy_24, "기쁨", "", Color.White, Color.Black, Color.White, Color.Black),
        MoodItem(com.sowhat.designsystem.R.drawable.ic_sad_24, "슬픔", "", Color.White, Color.Black, Color.White, Color.Black),
        MoodItem(com.sowhat.designsystem.R.drawable.ic_angry_24, "화남", "", Color.White, Color.Black, Color.White, Color.Black),
        MoodItem(com.sowhat.designsystem.R.drawable.ic_surprise_24, "놀람", "", Color.White, Color.Black, Color.White, Color.Black),
    )

    var selectedMoods = remember {
        mutableStateListOf<String>()
    }

    Column {
        MoodChips(
            selectedMoods = selectedMoods,
            onClick = {
                if (it in selectedMoods) selectedMoods.remove(it)
                else selectedMoods.add(it)
            },
            moodItems = moods,
        )

        MoodChips(
            selectedMoods = selectedMoods,
            onClick = {
                if (it in selectedMoods) selectedMoods.remove(it)
                else selectedMoods.add(it)
            },
            moodItems = moods,
            isActive = false
        )

    }

}