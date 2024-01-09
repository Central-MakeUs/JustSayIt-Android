package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun TextDrawableEnd(
    text: String,
    textStyle: TextStyle,
    textColor: Color,
    drawable: Int?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = textStyle.copy(
                color = textColor
            )
        )
        drawable?.let {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = "icon",
                tint = textColor
            )
        }
    }
}