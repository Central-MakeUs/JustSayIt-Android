package com.sowhat.report_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.sowhat.designsystem.component.TextDrawableEnd
import com.sowhat.designsystem.theme.Gray500

@Composable
fun Report(
    modifier: Modifier = Modifier,
    nickname: String,
    isActive: Boolean,
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
        ReportTitle(nickname = nickname)
        ReportCard(isActive = isActive)
        ReportButton(onClick = {})
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
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceXXS)
    ) {
        Text(
            text = stringResource(id = R.string.report_title),
            style = JustSayItTheme.Typography.body2,
            color = Gray500
        )

        Text(
            text = stringResource(id = R.string.report_subtitle, nickname),
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
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = JustSayItTheme.Shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = JustSayItTheme.Colors.mainBackground,
            contentColor = contentColorFor(backgroundColor = JustSayItTheme.Colors.mainBackground)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = JustSayItTheme.Spacing.spaceBase,
                    vertical = JustSayItTheme.Spacing.spaceXL
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceLg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = JustSayItTheme.Spacing.spaceLg),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(JustSayItTheme.Spacing.spaceBase)
            ) {
                // 슬라이더
                DateSelection()

                // 감정 선택 버튼
                CurrentMoodSelection(availableMoods = Mood.values().toList(), onChange = {})
            }

            DefaultButtonFull(
                text = stringResource(id = R.string.button_feeling_submit),
                isActive = isActive,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun ReportPreview() {
    Report(nickname = "케이엠", isActive = true)
}