package com.sowhat.user_presentation.common

data class MenuItem(
    val leadingIcon: Int? = null,
    val title: String,
    val trailingIcon: Int? = null,
    val trailingText: String? = null,
    val onClick: (() -> Unit)? = null
)
