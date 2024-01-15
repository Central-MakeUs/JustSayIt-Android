package com.sowhat.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = title,
            style = JustSayItTheme.Typography.body2
        )
    }
}

@Composable
fun DefaultHeader(title: String) {
    Text(
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = JustSayItTheme.Typography.body1,
        color = JustSayItTheme.Colors.mainTypo
    )
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun HeaderPreview() {
    Header(
        modifier = Modifier.fillMaxWidth(),
        title = "Header title"
    )
}