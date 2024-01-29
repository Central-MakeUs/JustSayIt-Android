package com.sowhat.report_presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.common.Mood

@Composable
fun CurrentMood(
    modifier: Modifier = Modifier,
    mood: Mood
) {
    mood.drawable?.let {
        Image(
            modifier = modifier.size(80.dp),
            painter = painterResource(id = it),
            contentDescription = "mood"
        )
    }
}

@Preview
@Composable
fun CurrentMoodPreview() {
    Column {
        CurrentMood(mood = Mood.HAPPY)
        CurrentMood(mood = Mood.SAD)
        CurrentMood(mood = Mood.SURPRISED)
        CurrentMood(mood = Mood.ANGRY)
    }
}