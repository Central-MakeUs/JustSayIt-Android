package com.sowhat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.MoodItem
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.theme.Black
import com.sowhat.designsystem.theme.Gray300
import com.sowhat.designsystem.theme.Gray900
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.theme.White

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    moodItem: MoodItem,
    isActive: Boolean,
    isSelected: Boolean,
    drawableStart: Int?,
    drawableSize: Dp,
    textStyle: TextStyle,
    backgroundColor: Color,
    title: String,
    textColor: Color,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(color = backgroundColor)
            .border(
                width = 1.dp,
                color = Gray300,
                shape = RoundedCornerShape(50)
            )
            .composed {
                if (isActive) {
                    rippleClickable {
                        onClick(moodItem.title)
                    }
                } else {
                    this
                }
            }
    ) {
        Row(
            modifier = modifier
                .padding(
                    start = JustSayItTheme.Spacing.spaceSm,
                    end = JustSayItTheme.Spacing.spaceBase,
                    top = JustSayItTheme.Spacing.spaceXS,
                    bottom = JustSayItTheme.Spacing.spaceXS
                ),
            horizontalArrangement = Arrangement
                .spacedBy(JustSayItTheme.Spacing.spaceXS),
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
fun MoodChip(
    modifier: Modifier = Modifier,
    moodItem: MoodItem,
    isActive: Boolean,
    isSelected: Boolean,
    drawableStart: Int?,
    drawableSize: Dp,
    textStyle: TextStyle,
    backgroundColor: Color,
    title: String,
    textColor: Color,
    onClick: (MoodItem) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(color = backgroundColor)
            .border(
                width = 1.dp,
                color = Gray300,
                shape = RoundedCornerShape(50)
            )
            .composed {
                if (isActive) {
                    rippleClickable {
                        onClick(moodItem)
                    }
                } else {
                    this
                }
            }
    ) {
        Row(
            modifier = modifier
                .padding(
                    horizontal = JustSayItTheme.Spacing.spaceSm,
                    vertical = JustSayItTheme.Spacing.spaceXXS
                ),
            horizontalArrangement = Arrangement
                .spacedBy(JustSayItTheme.Spacing.spaceXS),
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
    isActive: Boolean = true,
    isSelected: Boolean,
    moodItem: MoodItem,
    onClick: (String) -> Unit
) {
    Chip(
        isSelected = isSelected,
        isActive = isActive,
        modifier = modifier,
        drawableStart = moodItem.drawable,
        drawableSize = 24.dp,
        textStyle = JustSayItTheme.Typography.body1,
        title = moodItem.title,
        onClick = onClick,
        textColor = if (isSelected) moodItem.selectedTextColor else moodItem.unselectedTextColor,
        backgroundColor = if (isSelected) moodItem.selectedBackgroundColor else moodItem.unselectedBackgroundColor,
        moodItem = moodItem
    )
}

@Composable
fun ChipSm(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    isSelected: Boolean,
    moodItem: MoodItem,
    onClick: (MoodItem) -> Unit
) {
    MoodChip(
        isSelected = isSelected,
        isActive = isActive,
        modifier = modifier,
        drawableStart = moodItem.drawable,
        drawableSize = 24.dp,
        textStyle = JustSayItTheme.Typography.detail1,
        title = moodItem.title,
        onClick = onClick,
        textColor = if (isSelected) moodItem.selectedTextColor else moodItem.unselectedTextColor,
        backgroundColor = if (isSelected) moodItem.selectedBackgroundColor else moodItem.unselectedBackgroundColor,
        moodItem = moodItem
    )
}

@Composable
fun ChipSmall(
    isActive: Boolean,
    isSelected: Boolean,
    moodItem: MoodItem,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Chip(
        isSelected = isSelected,
        modifier = modifier,
        drawableStart = moodItem.drawable,
        drawableSize = JustSayItTheme.Spacing.spaceMd,
        textStyle = JustSayItTheme.Typography.detail1,
        title = moodItem.title,
        onClick = onClick,
        textColor = if (isSelected) moodItem.selectedTextColor else moodItem.unselectedTextColor,
        backgroundColor = if (isSelected) moodItem.selectedBackgroundColor else moodItem.unselectedBackgroundColor,
        moodItem = moodItem,
        isActive = isActive
    )
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun ChipPreview() {
    
    var isSelected by remember {
        mutableStateOf(true)
    }

    val textColor = if (isSelected) White else Gray900
    val backgroundColor = if (isSelected) Black else White
    
    Column {
        ChipMedium(
            isSelected = isSelected,
            onClick = { item: String -> },
            moodItem = MoodItem(
                drawable = R.drawable.ic_happy_24,
                title = "행복",
                selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
                unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground,
                selectedTextColor = JustSayItTheme.Colors.mainBackground,
                unselectedTextColor = JustSayItTheme.Colors.mainTypo,
                postData = ""
            )
        )

        ChipSmall(
            isSelected = isSelected,
            onClick = {},
            moodItem = MoodItem(
                drawable = R.drawable.ic_happy_24,
                title = "행복",
                postData = "",
                selectedBackgroundColor = JustSayItTheme.Colors.mainTypo,
                unselectedBackgroundColor = JustSayItTheme.Colors.mainBackground,
                selectedTextColor = JustSayItTheme.Colors.mainBackground,
                unselectedTextColor = JustSayItTheme.Colors.mainTypo
            ),
            isActive = true
        )
    }
}