package com.sowhat.post_presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.post_presentation.common.SubjectItem

@Composable
fun CurrentMoodSelection(
    modifier: Modifier = Modifier,
    subjectItem: SubjectItem,
    currentMood: MoodItem,
    onChange: (MoodItem) -> Unit,
    moodItems: List<MoodItem>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = JustSayItTheme.Spacing.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceNormal)
    ) {
        PostSubject(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JustSayItTheme.Spacing.spaceMedium),
            title = subjectItem.title,
            subTitle = subjectItem.subTitle
        )

        MoodChips(
            currentMood = currentMood,
            onChange = onChange,
            moodItems = moodItems
        )
    }
}

// 복수 선택
@Composable
fun SympathySelection(
    modifier: Modifier = Modifier,
    subjectItem: SubjectItem,
    onChange: (MoodItem) -> Unit,
    selectedMoods: List<MoodItem>,
    moodItems: List<MoodItem>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = JustSayItTheme.Spacing.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceNormal)
    ) {
        PostSubject(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JustSayItTheme.Spacing.spaceMedium),
            title = subjectItem.title,
            subTitle = subjectItem.subTitle
        )

        MoodChips(
            modifier = Modifier,
            onChange = onChange,
            selectedMoods = selectedMoods,
            moodItems = moodItems
        )
    }
}