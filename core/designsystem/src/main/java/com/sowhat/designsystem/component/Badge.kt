package com.sowhat.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sowhat.designsystem.R
import com.sowhat.designsystem.theme.Gray200
import com.sowhat.designsystem.theme.Gray400
import com.sowhat.designsystem.theme.JustSayItTheme

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    iconTint: Color,
    drawable: Int?
) {
    Box(
        modifier = modifier
            .clip(JustSayItTheme.Shapes.circle)
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        drawable?.let {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = "badge icon",
                tint = iconTint
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun BadgePreview() {
    Badge(
        modifier = Modifier.size(48.dp),
        backgroundColor = Gray200,
        iconTint = Gray400,
        drawable = R.drawable.ic_camera_24
    )
}