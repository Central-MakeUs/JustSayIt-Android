package com.sowhat.authentication_presentation.common

data class TextFieldInfo(
    val title: String,
    val value: String,
    val placeholder: String,
    val onValueChange: (String) -> Unit
)