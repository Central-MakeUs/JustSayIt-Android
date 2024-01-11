package com.sowhat.user_presentation.common

data class MenuItem(
    var leadingIcon: Int?,
    var title: String,
    var trailingIcon: Int?,
    var trailingText: String?,
    var onClick: (() -> Unit)? = null
) {
    constructor(
        title: String,
        trailingIcon: Int,
        onClick: (() -> Unit)? = null
    ): this(
        leadingIcon = null,
        title = title,
        trailingIcon = trailingIcon,
        trailingText = null,
        onClick = onClick,
    )

    constructor(
        title: String,
        trailingText: String,
        onClick: (() -> Unit)? = null
    ): this(
        leadingIcon = null,
        title = title,
        trailingIcon = null,
        trailingText = trailingText,
        onClick = onClick,
    )

    constructor(
        leadingIcon: Int,
        title: String,
        trailingText: String,
        onClick: (() -> Unit)? = null
    ): this(
        leadingIcon = leadingIcon,
        title = title,
        trailingIcon = null,
        trailingText = trailingText,
        onClick = onClick,
    )

    constructor(
        leadingIcon: Int,
        title: String,
        trailingIcon: Int,
        onClick: (() -> Unit)? = null
    ): this(
        leadingIcon = leadingIcon,
        title = title,
        trailingIcon = trailingIcon,
        trailingText = null,
        onClick = onClick,
    )
}
