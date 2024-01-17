package com.sowhat.designsystem.common

import androidx.compose.ui.graphics.Color
import com.sowhat.designsystem.theme.JustSayItTheme

data class MoodItem(
    val drawable: Int,
    val title: String,
    val postData: String, // 서버에 실질적으로 보낼 데이터
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val unselectedBackgroundColor: Color,
    val selectedBackgroundColor: Color,
)
