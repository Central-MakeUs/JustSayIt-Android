package com.sowhat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.sowhat.designsystem.common.Emotion
import com.sowhat.designsystem.common.rippleClickable
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray500
import com.sowhat.designsystem.theme.Gray900
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.designsystem.theme.White

@Composable
fun DefaultButton(
    text: String,
    textStyle: TextStyle = JustSayItTheme.Typography.body1,
    drawable: Int? = null,
    backgroundColor: Color = JustSayItTheme.Colors.subBackground,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        contentPadding = PaddingValues(0.dp),
        shape = JustSayItTheme.Shapes.medium,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding
                ),
            contentAlignment = Alignment.Center
        ) {
            TextDrawableEnd(
                text = text,
                textStyle = textStyle,
                textColor = textColor,
                drawable = drawable
            )
        }
    }
}

@Composable
fun DefaultButtonFull(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = JustSayItTheme.Typography.body1,
    drawable: Int? = null,
    backgroundColor: Color = JustSayItTheme.Colors.subBackground,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        contentPadding = PaddingValues(0.dp),
        shape = JustSayItTheme.Shapes.medium,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 13.dp),
            contentAlignment = Alignment.Center
        ) {
            TextDrawableEnd(
                text = text,
                textStyle = textStyle,
                textColor = textColor,
                drawable = drawable
            )
        }
    }
}

@Composable
fun DefaultButtonFull(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = JustSayItTheme.Typography.body1,
    drawable: Int? = null,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) Gray900 else Gray200
        ),
        contentPadding = PaddingValues(0.dp),
        shape = JustSayItTheme.Shapes.medium,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 13.dp),
            contentAlignment = Alignment.Center
        ) {
            TextDrawableEnd(
                text = text,
                textStyle = textStyle,
                textColor = if (isActive) White else Gray500,
                drawable = drawable
            )
        }
    }
}

@Composable
fun DefaultButtonDual(
    modifier: Modifier = Modifier,
    btnTextLeft: String,
    btnTextRight: String,
    textStyle: TextStyle = JustSayItTheme.Typography.body1,
    backgroundColor: Color = JustSayItTheme.Colors.subBackground,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClickBtnLeft: () -> Unit,
    onClickBtnRight: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DefaultButtonFull(
            text = btnTextLeft,
            textStyle = textStyle,
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClick = onClickBtnLeft
        )

        DefaultButtonFull(
            text = btnTextRight,
            textStyle = textStyle,
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClick = onClickBtnRight
        )
    }
}

@Composable
fun DefaultButtonDual(
    modifier: Modifier = Modifier,
    buttons: List<String>,
    activeButton: String?,
    textStyle: TextStyle = JustSayItTheme.Typography.body1,
    onClick: (String) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buttons.forEach { buttonItem ->
            DefaultButtonFull(
                modifier = Modifier.weight(1f),
                text = buttonItem,
                textStyle = textStyle,
                backgroundColor = if (activeButton == buttonItem) Gray900 else Gray200,
                textColor = if (activeButton == buttonItem) White else Gray500,
                onClick = { onClick(buttonItem) }
            )
        }
    }
}

@Composable
fun DefaultButtonExtraLarge(
    text: String,
    drawable: Int? = null,
    backgroundColor: Color = JustSayItTheme.Colors.subBackground,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textColor = textColor,
        drawable = drawable,
        backgroundColor = backgroundColor,
        horizontalPadding = 16.dp,
        verticalPadding = 13.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonExtraLarge(
    text: String,
    drawable: Int? = null,
    isActive: Boolean,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textColor = if (isActive) White else Gray500,
        drawable = drawable,
        backgroundColor = if (isActive) Gray900 else Gray200,
        horizontalPadding = 16.dp,
        verticalPadding = 13.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonLarge(
    text: String,
    drawable: Int? = null,
    backgroundColor: Color = JustSayItTheme.Colors.subBackground,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textColor = textColor,
        drawable = drawable,
        backgroundColor = backgroundColor,
        horizontalPadding = 16.dp,
        verticalPadding = 11.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonLarge(
    text: String,
    drawable: Int? = null,
    isActive: Boolean,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textColor = if (isActive) White else Gray500,
        drawable = drawable,
        backgroundColor = if (isActive) Gray900 else Gray200,
        horizontalPadding = 16.dp,
        verticalPadding = 11.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonMedium(
    text: String,
    drawable: Int? = null,
    backgroundColor: Color = JustSayItTheme.Colors.subBackground,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textColor = textColor,
        drawable = drawable,
        backgroundColor = backgroundColor,
        horizontalPadding = 16.dp,
        verticalPadding = 7.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonMedium(
    text: String,
    drawable: Int? = null,
    isActive: Boolean,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textColor = if (isActive) White else Gray500,
        drawable = drawable,
        backgroundColor = if (isActive) Gray900 else Gray200,
        horizontalPadding = 16.dp,
        verticalPadding = 7.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonSmall(
    text: String,
    drawable: Int? = null,
    backgroundColor: Color = JustSayItTheme.Colors.subBackground,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textStyle = JustSayItTheme.Typography.detail1,
        textColor = textColor,
        drawable = drawable,
        backgroundColor = backgroundColor,
        horizontalPadding = 8.dp,
        verticalPadding = 5.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonSmall(
    text: String,
    drawable: Int? = null,
    isActive: Boolean,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textStyle = JustSayItTheme.Typography.detail1,
        textColor = if (isActive) White else Gray500,
        drawable = drawable,
        backgroundColor = if (isActive) Gray900 else Gray200,
        horizontalPadding = 8.dp,
        verticalPadding = 5.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonTiny(
    text: String,
    drawable: Int? = null,
    backgroundColor: Color = JustSayItTheme.Colors.subBackground,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textStyle = JustSayItTheme.Typography.detail1,
        textColor = textColor,
        drawable = drawable,
        backgroundColor = backgroundColor,
        horizontalPadding = 8.dp,
        verticalPadding = 3.dp,
        onClick = onClick
    )
}

@Composable
fun DefaultButtonTiny(
    text: String,
    drawable: Int? = null,
    isActive: Boolean,
    onClick: () -> Unit
) {
    DefaultButton(
        text = text,
        textStyle = JustSayItTheme.Typography.detail1,
        textColor = if (isActive) White else Gray500,
        drawable = drawable,
        backgroundColor = if (isActive) Gray900 else Gray200,
        horizontalPadding = 8.dp,
        verticalPadding = 3.dp,
        onClick = onClick
    )
}

@Composable
fun EmotionButton(
    modifier: Modifier = Modifier,
    currentEmotion: Emotion,
    emotion: Emotion,
    onClick: (String) -> Unit
) {

    val tint = if (currentEmotion == emotion) emotion.selectedTint else emotion.unselectedTint

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(JustSayItTheme.Shapes.circle)
            .rippleClickable {
                onClick(emotion.title)
            }
            .background(
                color = tint,
                shape = JustSayItTheme.Shapes.circle
            )
    )
}

@Composable
fun SquaredIconButton(
    modifier: Modifier = Modifier,
    icon: Int?,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .border(
                width = 0.5.dp,
                color = JustSayItTheme.Colors.subSurface,
                shape = JustSayItTheme.Shapes.medium
            )
            .clip(JustSayItTheme.Shapes.medium)
            .rippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        icon?.let {
            Icon(
                painter = painterResource(id = it),
                tint = JustSayItTheme.Colors.subTypo,
                contentDescription = contentDescription
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DefaultButtonPreview() {
    Column {
        DefaultButton(
            text = "Button",
            horizontalPadding = 16.dp,
            verticalPadding = 16.dp,
            onClick = {}
        )
        DefaultButton(
            text = "Button",
            horizontalPadding = 16.dp,
            verticalPadding = 16.dp,
            drawable = R.drawable.ic_next_16,
            onClick = {}
        )
        DefaultButtonExtraLarge(
            text = "Button",
            isActive = true,
            onClick = {}
        )
        DefaultButtonExtraLarge(
            text = "Button",
            isActive = false,
            onClick = {}
        )
        DefaultButtonLarge(
            text = "Button",
            isActive = true,
            onClick = {}
        )
        DefaultButtonLarge(
            text = "Button",
            isActive = false,
            onClick = {}
        )
        DefaultButtonMedium(
            text = "Button",
            isActive = true,
            onClick = {}
        )
        DefaultButtonMedium(
            text = "Button",
            isActive = false,
            onClick = {}
        )
        DefaultButtonSmall(
            text = "Button",
            isActive = true,
            onClick = {}
        )
        DefaultButtonSmall(
            text = "Button",
            isActive = false,
            onClick = {}
        )
        DefaultButtonTiny(
            text = "Button",
            isActive = true,
            onClick = {}
        )
        DefaultButtonTiny(
            text = "Button",
            isActive = false,
            onClick = {}
        )
        DefaultButtonFull(
            text = "Button",
            onClick = {}
        )
        DefaultButtonFull(
            text = "Button",
            isActive = false,
            onClick = {}
        )
        DefaultButtonFull(
            text = "Button",
            isActive = true,
            onClick = {}
        )
        DefaultButtonDual(
            buttons = listOf("Button1", "Button2"),
            activeButton = "Button1",
            onClick = {}
        )
    }
}