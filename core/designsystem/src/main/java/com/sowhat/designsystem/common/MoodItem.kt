package com.sowhat.designsystem.common

import androidx.compose.ui.graphics.Color
import com.sowhat.designsystem.theme.JustSayItTheme

data class MoodItem(
    val drawable: Int,
    val title: String,
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val unselectedBackgroundColor: Color,
    val selectedBackgroundColor: Color,
    val onClick: (String) -> Unit
)
