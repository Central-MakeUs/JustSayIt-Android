package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun DefaultTextButton(
    text: String,
    drawable: Int? = null,
    textStyle: TextStyle = JustSayItTheme.Typography.body1,
    textColor: Color = JustSayItTheme.Colors.mainTypo,
    onClick: () -> Unit
) {
    TextButton(
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            TextDrawableEnd(
                text = text,
                textColor = textColor,
                drawable = drawable,
                textStyle = textStyle
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun TextButtonPreview() {
    Column {
        DefaultTextButton(text = "완료", textColor = Color.Black, onClick = {})
        DefaultTextButton(text = "완료", textColor = Color.Black, drawable = R.drawable.ic_next_16, onClick = {})
    }
}