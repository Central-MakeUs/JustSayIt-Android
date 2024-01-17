package com.sowhat.post_presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sowhat.designsystem.component.ChipMedium
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.post_presentation.common.SubjectItem

@Composable
fun MoodChips(
    modifier: Modifier = Modifier,
    currentMood: MoodItem,
    onChange: (MoodItem) -> Unit,
    moodItems: List<MoodItem>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceExtraSmall)
    ) {
        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceMedium))
        }

        itemsIndexed(moodItems) { _, moodItem ->
            val isSelected = currentMood == moodItem
            ChipMedium(
                moodItem = moodItem,
                isSelected = isSelected,
                onClick = { item ->
                    onChange(item)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceMedium))
        }
    }
}

@Composable
fun MoodChips(
    modifier: Modifier = Modifier,
    onChange: (MoodItem) -> Unit,
    selectedMoods: List<MoodItem>,
    moodItems: List<MoodItem>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceExtraSmall)
    ) {
        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceMedium))
        }

        itemsIndexed(moodItems) { _, moodItem ->
            val isSelected = moodItem in selectedMoods
            ChipMedium(
                moodItem = moodItem,
                isSelected = isSelected,
                onClick = { item ->
                    onChange(item)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.width(JustSayItTheme.Spacing.spaceMedium))
        }
    }
}