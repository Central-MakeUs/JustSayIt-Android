package com.sowhat.authentication_presentation.common

import androidx.compose.ui.Modifier

data class TextFieldInfo(
    val focusRequester: Modifier? = null,
    val title: String,
    val value: String,
    val placeholder: String,
    val onValueChange: (String) -> Unit,
    val onNext: (String) -> Unit
)