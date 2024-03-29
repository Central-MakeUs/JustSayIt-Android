package com.sowhat.report_presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.R
import com.sowhat.designsystem.R.drawable.ic_next_16
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.noRippleClickable
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.component.DefaultButtonFull
import com.sowhat.designsystem.component.ImageButton
import com.sowhat.designsystem.component.TextDrawableEnd
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.report_presentation.common.ReportUiState
import com.sowhat.report_presentation.common.TodayMoodItem

@Composable
fun Report(
    modifier: Modifier = Modifier,
    reportUiState: ReportUiState,
    nickname: String,
    isActive: Boolean,
    selectedMood: Mood?,
    onSelectedMoodChange: (Mood?) -> Unit,
    onMoodSubmit: () -> Unit,
    todayMoodItems: List<TodayMoodItem>
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = JustSayItTheme.Spacing.spaceBase,
                vertical = JustSayItTheme.Spacing.spaceLg
            )
            .background(JustSayItTheme.Colors.mainBackground),
        verticalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceBase)
    ) {
        ReportTitle(
            nickname = nickname,
            isLoading = reportUiState.isLoading
        )
        ReportCard(
            isActive = isActive,
            selectedMood = selectedMood,
            onSelectedMoodChange = onSelectedMoodChange,
            onMoodSubmit = onMoodSubmit,
            todayMoodItems = todayMoodItems
        )
//        ReportButton(onClick = {})
    }
}

@Composable
fun ReportButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        TextDrawableEnd(
            modifier = Modifier
                .noRippleClickable { onClick() },
            text = stringResource(id = R.string.report_view_report),
            textStyle = JustSayItTheme.Typography.body3,
            textColor = JustSayItTheme.Colors.mainTypo,
            drawable = ic_next_16
        )
    }
}

@Composable
private fun ReportTitle(
    modifier: Modifier = Modifier,
    nickname: String,
    isLoading: Boolean
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceXXS)
    ) {
        Text(
            modifier = Modifier.padding(bottom = JustSayItTheme.Spacing.spaceTiny),
            text = stringResource(id = R.string.report_title),
            style = JustSayItTheme.Typography.body2,
            color = Gray500
        )

        Text(
            text = stringResource(
                id = R.string.report_subtitle,
                nickname
            ),
            style = JustSayItTheme.Typography.heading2,
            color = JustSayItTheme.Colors.mainTypo,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ReportCard(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    selectedMood: Mood?,
    onSelectedMoodChange: (Mood?) -> Unit,
    onMoodSubmit: () -> Unit,
    todayMoodItems: List<TodayMoodItem>
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = JustSayItTheme.Shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = JustSayItTheme.Colors.mainBackground,
            contentColor = contentColorFor(backgroundColor = JustSayItTheme.Colors.mainBackground)
        ),
        border = BorderStroke(width = JustSayItTheme.Spacing.border, color = Gray300)
    ) {
        ReportContent(
            selectedMood = selectedMood,
            onSelectedMoodChange = onSelectedMoodChange,
            isActive = isActive,
            onMoodSubmit = onMoodSubmit,
            todayMoodItems = todayMoodItems
        )
    }
}

@Composable
private fun ReportContent(
    selectedMood: Mood?,
    onSelectedMoodChange: (Mood?) -> Unit,
    isActive: Boolean,
    onMoodSubmit: () -> Unit,
    todayMoodItems: List<TodayMoodItem>
) {
    Column(
        modifier = Modifier
            .padding(JustSayItTheme.Spacing.spaceBase),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceBase)
    ) {
        CurrentMoodSelection(
            selectedMood = selectedMood,
            onSelectedMoodChange = onSelectedMoodChange,
            isActive = isActive,
            onMoodSubmit = onMoodSubmit
        )

        MoodTracker(
            moodList = todayMoodItems
        )
    }
}

@Composable
private fun CurrentMoodSelection(
    selectedMood: Mood?,
    onSelectedMoodChange: (Mood?) -> Unit,
    isActive: Boolean,
    onMoodSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceSm)
    ) {
        // 감정 선택 버튼
        MoodSelectionButtons(
            moodItems = Mood.values().toList(),
            selectedItem = selectedMood,
            onChange = onSelectedMoodChange
        )

        DefaultButtonFull(
            text = stringResource(id = R.string.button_feeling_submit),
            isActive = isActive,
            onClick = onMoodSubmit
        )
    }
}

@Composable
fun MoodTracker(
    modifier: Modifier = Modifier,
    moodList: List<TodayMoodItem>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = JustSayItTheme.Spacing.spaceXS),
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceSm)
    ) {
        Text(
            text = stringResource(id = R.string.report_tracker),
            style = JustSayItTheme.Typography.body4,
            color = JustSayItTheme.Colors.mainTypo
        )

        TodayMoodList(moodList)
    }
}

@Composable
private fun TodayMoodList(moodList: List<TodayMoodItem>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceMd)
    ) {
        itemsIndexed(moodList) { _, moodItem ->
            TodayMoodItem(moodItem)
        }
    }
}

@Composable
private fun TodayMoodItem(moodItem: TodayMoodItem) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceXXS)
    ) {
        moodItem.mood?.drawable?.let { drawable ->
            Image(
                modifier = Modifier.size(JustSayItTheme.Spacing.spaceLg),
                painter = painterResource(id = drawable),
                contentDescription = "today"
            )
        }

        Text(
            modifier = Modifier.padding(end = JustSayItTheme.Spacing.border),
            text = moodItem.time,
            style = JustSayItTheme.Typography.detail1,
            color = JustSayItTheme.Colors.mainTypo
        )
    }
}

@Composable
fun MoodSelectionButtons(
    modifier: Modifier = Modifier,
    selectedItem: Mood?,
    moodItems: List<Mood>,
    onChange: (Mood?) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        moodItems.forEach { mood ->
            mood.drawable?.let { image ->
                val isSelected = selectedItem == mood
                val alpha by animateFloatAsState(
                    targetValue = if (selectedItem == null || isSelected) 1f else 0.3f,
                    label = ""
                )

                MoodButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(alpha),
                    selectedItem = selectedItem,
                    mood = mood,
                    drawable = image,
                    onChange = onChange
                )
            }
        }
    }
}

@Composable
private fun MoodButton(
    modifier: Modifier = Modifier,
    selectedItem: Mood?,
    mood: Mood,
    drawable: Int,
    onChange: (Mood?) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(JustSayItTheme.Shapes.medium)
            .composed {
                if (selectedItem == mood) {
                    border(
                        width = 1.dp,
                        color = JustSayItTheme.Colors.mainTypo,
                        shape = JustSayItTheme.Shapes.medium
                    )
                } else this
            },
        contentAlignment = Alignment.Center
    ) {
        ImageButton(
            modifier = Modifier.size(48.dp),
            imageDrawable = drawable,
            onClick = {
                if (selectedItem == mood) {
                    onChange(null)
                } else {
                    onChange(mood)
                }
            }
        )
    }
}

@Preview
@Composable
fun ReportPreview() {
    Report(
        nickname = "케이엠",
        isActive = true,
        selectedMood = Mood.HAPPY,
        onSelectedMoodChange = {},
        onMoodSubmit = {},
        todayMoodItems = listOf(
            TodayMoodItem(Mood.HAPPY, "12:00"), TodayMoodItem(Mood.HAPPY, "12:00")
        ),
        reportUiState = ReportUiState()
    )
}