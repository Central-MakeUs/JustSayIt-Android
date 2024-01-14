package com.sowhat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    drawableStart: Int?,
    drawableSize: Dp,
    textStyle: TextStyle,
    title: String,
    textColor: Color,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .border(
                width = 1.dp,
                color = Gray300,
                shape = RoundedCornerShape(50)
            )
            .rippleClickable {
                onClick(title)
            }
    ) {
        Row(
            modifier = modifier
                .padding(
                    horizontal = JustSayItTheme.Spacing.spaceNormal,
                    vertical = JustSayItTheme.Spacing.spaceExtraSmall
                ),
            horizontalArrangement = Arrangement
                .spacedBy(JustSayItTheme.Spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {

            drawableStart?.let {
                Image(
                    modifier = Modifier.size(drawableSize),
                    painter = painterResource(id = drawableStart),
                    contentDescription = "chip_icon"
                )
            }

            Text(
                text = title,
                style = textStyle,
                color = textColor
            )
        }
    }
}

@Composable
fun ChipMedium(
    modifier: Modifier = Modifier,
    drawableStart: Int?,
    title: String,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: (String) -> Unit
) {
    Chip(
        modifier = modifier,
        drawableStart = drawableStart,
        drawableSize = 24.dp,
        textStyle = JustSayItTheme.Typography.body1,
        title = title,
        onClick = onClick,
        textColor = textColor
    )
}

@Composable
fun ChipSmall(
    modifier: Modifier = Modifier,
    drawableStart: Int?,
    title: String,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: (String) -> Unit
) {
    Chip(
        modifier = modifier,
        drawableStart = drawableStart,
        drawableSize = JustSayItTheme.Spacing.spaceLarge,
        textStyle = JustSayItTheme.Typography.detail1,
        title = title,
        onClick = onClick,
        textColor = textColor
    )
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun ChipPreview() {
    Column {
        ChipMedium(
            drawableStart = R.drawable.ic_happy_24,
            title = "행복",
            textColor = JustSayItTheme.Colors.mainTypo,
            onClick = {}
        )

        ChipSmall(
            drawableStart = R.drawable.ic_happy_24,
            title = "행복",
            textColor = JustSayItTheme.Colors.mainTypo,
            onClick = {}
        )
    }
}