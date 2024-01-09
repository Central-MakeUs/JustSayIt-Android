package com.sowhat.designsystem.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.normalShadow(): Modifier = composed {
    shadow(
        elevation = 10.dp,
        spotColor = Color(0x1A000000),
        ambientColor = Color(0x1A000000)
    )
}