package com.sowhat.designsystem.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.rippleClickable(
    onClick: () -> Unit
): Modifier = composed {
    clickable (
        interactionSource = remember {
            MutableInteractionSource()
        },
        indication = rememberRipple(),
        onClick = onClick
    )
}

fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier = composed {
    clickable (
        interactionSource = remember {
            MutableInteractionSource()
        },
        indication = null,
        onClick = onClick
    )
}