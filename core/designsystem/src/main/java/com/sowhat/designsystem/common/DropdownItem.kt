package com.sowhat.designsystem.common

import com.sowhat.designsystem.R

data class DropdownItem(
    val title: String,
    val drawable: Int? = null,
    val postData: String? = null,
    val onItemClick: (() -> Unit)? = null
)

val feedDropdownItems = listOf(
    DropdownItem(title = "전체", postData = null, drawable = null),
    DropdownItem(title = "행복", postData = MOOD_HAPPY, drawable = R.drawable.ic_happy_96),
    DropdownItem(title = "슬픔", postData = MOOD_SAD, drawable = R.drawable.ic_sad_96),
    DropdownItem(title = "놀람", postData = MOOD_SURPRISED, drawable = R.drawable.ic_surprise_96),
    DropdownItem(title = "화남", postData = MOOD_ANGRY, drawable = R.drawable.ic_angry_96),
)
