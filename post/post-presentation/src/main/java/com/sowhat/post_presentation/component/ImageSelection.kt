package com.sowhat.post_presentation.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.component.SquaredIconButton
import com.sowhat.designsystem.component.SquaredImageContainer
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.post_presentation.R

@Composable
fun ImageSelection(
    modifier: Modifier = Modifier,
    images: List<Uri>,
    onAddClick: () -> Unit,
    onImagesChange: (List<Uri>) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(
                start = JustSayItTheme.Spacing.spaceMedium,
                end = JustSayItTheme.Spacing.spaceMedium,
                bottom = JustSayItTheme.Spacing.spaceMedium,
            ),
        horizontalArrangement = Arrangement
            .spacedBy(JustSayItTheme.Spacing.spaceExtraSmall)
    ) {
        SquaredIconButton(
            modifier = Modifier.background(Gray200),
            icon = com.sowhat.designsystem.R.drawable.ic_camera_24,
            onClick = onAddClick
        )

        images.forEachIndexed { removeIndex, uri ->
            SquaredImageContainer(
                modifier = modifier
                    .rippleClickable {
                        val changedList = images.filterIndexed { index, _ ->
                            removeIndex != index
                        }
                        onImagesChange(changedList)
                    },
                model = uri
            )
        }
    }
}

@Preview
@Composable
fun ImageSelectionPreview() {
    ImageSelection(images = listOf(), onAddClick = { /*TODO*/ }, onImagesChange = {})
}