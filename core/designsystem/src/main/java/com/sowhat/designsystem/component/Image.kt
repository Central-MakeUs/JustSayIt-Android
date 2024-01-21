package com.sowhat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CornerBasedShape
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
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.theme.ProfileBackground

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    borderWidth: Dp? = null,
    borderColor: Color? = null,
    shape: CornerBasedShape? = null,
    model: Any?,
    contentDescription : String?
) {
    Box(
        modifier = modifier.composed {
            if (borderWidth != null && borderColor != null && shape != null) {
                border(width = borderWidth, color = borderColor, shape = shape)
                clip(shape)
            } else {
                this
            }
        },
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = model,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    borderWidth: Dp,
    borderColor: Color,
    shape: CornerBasedShape,
    model: Any?,
    contentDescription : String?
) {
    Box(
        modifier = modifier
            .border(width = borderWidth, color = borderColor, shape = shape)
            .clip(shape)
            .background(ProfileBackground)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = model,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun SquaredImageContainer(
    modifier: Modifier = Modifier,
    model: Any?,
    contentDescription: String? = null
) {
    ImageContainer(
        modifier = modifier.aspectRatio(1f),
        borderWidth = 0.5.dp,
        borderColor = JustSayItTheme.Colors.subSurface,
        shape = JustSayItTheme.Shapes.large,
        model = model,
        contentDescription = contentDescription
    )
}

@Composable
fun TimelineFeedImageContainer(
    modifier: Modifier = Modifier,
    model: Any?,
    contentDescription: String? = null
) {
    ImageContainer(
        modifier = modifier,
        borderWidth = 0.5.dp,
        borderColor = JustSayItTheme.Colors.subSurface,
        shape = JustSayItTheme.Shapes.medium,
        model = model,
        contentDescription = contentDescription
    )
}

@Composable
fun TimelineFeedImages(
    modifier: Modifier = Modifier,
    models: List<String>
) {
    val spacing = JustSayItTheme.Spacing.spaceTiny

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        when (models.size) {
            0 -> {}
            1 -> {
                models.forEach {
                    TimelineFeedImageContainer(
                        // 간격이 동일하게 주어지도록 하기 위함... 이 조치를 취해주지 않으면 사진들이 화면을 벗어나서 차지
                        modifier = Modifier
                            .padding(JustSayItTheme.Spacing.spaceTiny),
                        model = it
                    )
                }
            }
            2 -> {
                Column(
                    modifier = Modifier,
                ) {
                    models.forEach {
                        TimelineFeedImageContainer(
                            modifier = Modifier
                                .padding(JustSayItTheme.Spacing.spaceTiny)
                                // 간격이 동일하게 주어지도록 하기 위함... 이 조치를 취해주지 않으면 사진들이 화면을 벗어나서 차지
                                .weight(2f),
                            model = it
                        )
                    }
                }
            }
            3 -> {
                LazyVerticalGrid(
                    modifier = Modifier,
                    columns = GridCells.Fixed(2)
                ) {
                    itemsIndexed(
                        items = models,
                        span = { index: Int, _: String ->
                            val span = if (index == 0) 2 else 1
                            GridItemSpan(span)
                        }
                    ) {index, item ->
                        val ratio = if (index == 0) 2f else 1f
                        
                        TimelineFeedImageContainer(
                            modifier = Modifier
                                .padding(JustSayItTheme.Spacing.spaceTiny)
                                .aspectRatio(ratio),
                            model = item
                        )
                    }
                }
            }
            4 -> {
                LazyVerticalGrid(
                    modifier = Modifier,
                    columns = GridCells.Fixed(2)
                ) {
                    items(items = models) {
                        TimelineFeedImageContainer(
                            modifier = Modifier
                                .padding(JustSayItTheme.Spacing.spaceTiny)
                                .aspectRatio(1f),
                            model = it
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileImageContainerPreview() {
    LazyColumn {
        item {
            SquaredImageContainer(
                modifier = Modifier.size(36.dp),
                model = "https://i.stack.imgur.com/6C9Qv.png"
            )

            Spacer(modifier = Modifier.height(16.dp))

            TimelineFeedImages(models = listOf(
                "https://i.stack.imgur.com/6C9Qv.png",
            ))


            TimelineFeedImages(models = listOf(
                "https://i.stack.imgur.com/6C9Qv.png",
                "https://i.stack.imgur.com/6C9Qv.png",
            ))

            TimelineFeedImages(models = listOf(
                "https://i.stack.imgur.com/6C9Qv.png",
                "https://i.stack.imgur.com/6C9Qv.png",
                "https://i.stack.imgur.com/6C9Qv.png",
            ))

            TimelineFeedImages(models = listOf(
                "https://i.stack.imgur.com/6C9Qv.png",
                "https://i.stack.imgur.com/6C9Qv.png",
                "https://i.stack.imgur.com/6C9Qv.png",
                "https://i.stack.imgur.com/6C9Qv.png",
            ))
        }

    }



}