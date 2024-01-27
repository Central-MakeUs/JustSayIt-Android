package com.sowhat.designsystem.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color, horizontalPadding: Dp = 0.dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val horizontalPaddingPx = density.run { horizontalPadding.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f + horizontalPaddingPx, y = height),
                end = Offset(x = width - horizontalPaddingPx , y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.topBorder(strokeWidth: Dp, color: Color, horizontalPadding: Dp = 0.dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val horizontalPaddingPx = density.run { horizontalPadding.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f + horizontalPaddingPx, y = 0f),
                end = Offset(x = width - horizontalPaddingPx , y = 0f),
                strokeWidth = strokeWidthPx
            )
        }
    }
)



fun Modifier.dashedBorder(width: Float, color: Color, cornerRadius: Dp) = composed (
    factory = {
        val dashedStroke = Stroke(
            width = width,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
        )

        Modifier.drawBehind {
            drawRoundRect(
                color = color,
                style = dashedStroke,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
    }
)



