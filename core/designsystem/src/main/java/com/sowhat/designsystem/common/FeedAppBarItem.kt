package com.sowhat.designsystem.common

import androidx.compose.ui.graphics.Color
import com.sowhat.designsystem.theme.AngryColor
import com.sowhat.designsystem.theme.HappyColor
import com.sowhat.designsystem.theme.SadColor
import com.sowhat.designsystem.theme.SurpriseColor

enum class Emotion(
    val title: String,
    val selectedTint: Color,
    val unselectedTint: Color
) {
    ANGRY("angry", AngryColor, AngryColor.copy(alpha = 0.5f)),
    HAPPY("happy", HappyColor, HappyColor.copy(alpha = 0.5f)),
    SURPRISE("surprise", SurpriseColor, SurpriseColor.copy(alpha = 0.5f)),
    SAD("sad", SadColor, SadColor.copy(alpha = 0.5f))
}