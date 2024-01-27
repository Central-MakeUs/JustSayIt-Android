package com.sowhat.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun TextDrawableStart(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    textColor: Color,
    drawable: Int?,
    drawableSize: Dp?
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (drawable != null && drawableSize != null) {
            Image(
                modifier = Modifier.size(drawableSize),
                painter = painterResource(id = drawable),
                contentDescription = "icon",
            )

            Spacer(modifier = Modifier.width(10.dp))
        }

        Text(
            modifier = Modifier,
            text = text,
            style = textStyle.copy(
                color = textColor
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TextDrawableEnd(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    textColor: Color,
    drawable: Int?
) {
    Row(
        modifier = modifier,
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