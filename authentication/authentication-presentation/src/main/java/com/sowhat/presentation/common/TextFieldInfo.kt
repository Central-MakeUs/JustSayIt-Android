package com.sowhat.presentation.common

import androidx.constraintlayout.helper.widget.MotionPlaceholder

data class TextFieldInfo(
    val title: String,
    val value: String,
    val placeholder: String,
    val onValueChange: (String) -> Unit
)