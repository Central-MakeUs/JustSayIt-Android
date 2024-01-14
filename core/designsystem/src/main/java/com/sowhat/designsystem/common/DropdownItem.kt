package com.sowhat.designsystem.common

data class DropdownItem(
    val title: String,
    val drawable: Int? = null,
    val onItemClick: () -> Unit
)
