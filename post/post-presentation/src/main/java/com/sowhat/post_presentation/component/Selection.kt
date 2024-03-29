package com.sowhat.post_presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.post_presentation.common.SubjectItem

@Composable
fun CurrentMoodSelection(
    modifier: Modifier = Modifier,
    subjectItem: SubjectItem,
    currentMood: MoodItem?,
    onChange: (MoodItem) -> Unit,
    moodItems: List<MoodItem>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = JustSayItTheme.Spacing.spaceBase),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceSm)
    ) {
        PostSubject(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JustSayItTheme.Spacing.spaceBase),
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
    isActive: Boolean = true,
    subjectItem: SubjectItem,
    onClick: (MoodItem) -> Unit,
    selectedMoods: List<MoodItem>,
    moodItems: List<MoodItem>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = JustSayItTheme.Spacing.spaceBase)
            .composed {
                if (!isActive) {
                    alpha(0.3f)
                } else this
            },
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceSm)

    ) {
        PostSubject(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JustSayItTheme.Spacing.spaceBase),
            isActivated = isActive,
            title = subjectItem.title,
            subTitle = subjectItem.subTitle
        )

        MoodChips(
            modifier = Modifier,
            isActive = isActive,
            onClick = onClick,
            selectedMoods = selectedMoods,
            moodItems = moodItems
        )
    }
}