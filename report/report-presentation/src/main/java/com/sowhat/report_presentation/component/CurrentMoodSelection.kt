package com.sowhat.report_presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.component.ImageButton
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun CurrentMoodSelection(
    modifier: Modifier = Modifier,
    availableMoods: List<Mood>,
    onChange: (Mood) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(
                space = JustSayItTheme.Spacing.spaceMd,
                alignment = Alignment.CenterHorizontally
            )
    ) {
        availableMoods.forEach { mood ->
            ImageButton(
                modifier = Modifier.size(36.dp),
                imageDrawable = mood.drawable,
                onClick = { onChange(mood) }
            )
        }
    }
}