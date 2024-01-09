package com.sowhat.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    borderWidth: Dp? = null,
    borderColor: Color? = null,
    shape: CornerBasedShape? = null,
    imageUrl: Any,
    contentDescription : String?
) {
    Box(
        modifier = modifier.composed {
            if (borderWidth != null && borderColor != null && shape != null) {
                border(width = borderWidth, color = borderColor)
                clip(shape)
            } else {
                this
            }
        },
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ProfileImageContainer(
    modifier: Modifier = Modifier,
    imageUrl: Any,
    contentDescription: String? = null
) {
    ImageContainer(
        modifier = modifier.aspectRatio(1f),
        borderWidth = 2.dp,
        borderColor = JustSayItTheme.Colors.subSurface,
        shape = JustSayItTheme.Shapes.large,
        imageUrl = imageUrl,
        contentDescription = contentDescription
    )
}

@Preview
@Composable
fun ProfileImageContainerPreview() {
    ProfileImageContainer(
        modifier = Modifier.size(36.dp),
        imageUrl = "https://i.stack.imgur.com/6C9Qv.png"
    )
}