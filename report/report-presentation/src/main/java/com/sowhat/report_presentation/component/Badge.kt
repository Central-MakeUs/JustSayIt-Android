package com.sowhat.report_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.R

@Composable
fun OpenStatusBadge(
    modifier: Modifier = Modifier,
    isOpen: Boolean
) {
    Row(
        modifier = modifier
            .width(76.dp)
            .height(28.dp)
            .clip(JustSayItTheme.Shapes.medium)
            .border(
                width = JustSayItTheme.Spacing.border,
                color = Gray300,
                shape = JustSayItTheme.Shapes.medium
            )
            .background(JustSayItTheme.Colors.mainBackground)
            .padding(
                top = JustSayItTheme.Spacing.spaceXXS,
                bottom = JustSayItTheme.Spacing.spaceXXS,
                start = JustSayItTheme.Spacing.spaceXS,
                end = JustSayItTheme.Spacing.spaceSm
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceXXS)
    ) {
        val drawable = if (isOpen) R.drawable.ic_unlock_16 else R.drawable.ic_lock_16
        val textRes = if (isOpen) R.string.report_badge_public else R.string.report_badge_private

        Icon(
            painter = painterResource(id = drawable),
            contentDescription = "is_open",
            tint = JustSayItTheme.Colors.inactiveTypo
        )

        Text(
            modifier = Modifier.width(JustSayItTheme.Spacing.spaceXL),
            text = stringResource(id = textRes),
            textAlign = TextAlign.End,
            style = JustSayItTheme.Typography.detail1,
            color = JustSayItTheme.Colors.mainTypo
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OpenBadgePreview() {
    Column {
        OpenStatusBadge(isOpen = true)
        OpenStatusBadge(isOpen = false)
    }
}