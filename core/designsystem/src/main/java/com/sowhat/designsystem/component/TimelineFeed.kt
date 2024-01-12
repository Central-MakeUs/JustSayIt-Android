package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.sowhat.designsystem.common.Emotion
import com.sowhat.designsystem.theme.JustSayItTheme

// https://tourspace.tistory.com/416
// https://proandroiddev.com/a-step-by-step-guide-to-building-a-timeline-component-with-jetpack-compose-358a596847cb
@Composable
fun TimelineFeed(
    modifier: Modifier = Modifier,
    emotion: Emotion,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .wrapContentHeight()
            .padding(horizontal = JustSayItTheme.Spacing.spaceNormal)
    ) {

    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun TimelineFeedPreview() {

}