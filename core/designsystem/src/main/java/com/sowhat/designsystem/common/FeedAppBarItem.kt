package com.sowhat.designsystem.common

import androidx.compose.ui.graphics.Color
import com.sowhat.designsystem.theme.AngryColor
import com.sowhat.designsystem.theme.HappyColor
import com.sowhat.designsystem.theme.SadColor
import com.sowhat.designsystem.theme.SurpriseColor

enum class Emotion(
    val title: String,
    val color: Color
) {
    ANGRY("angry", AngryColor),
    HAPPY("happy", HappyColor),
    SURPRISE("surprise", SurpriseColor),
    SAD("sad", SadColor)
}