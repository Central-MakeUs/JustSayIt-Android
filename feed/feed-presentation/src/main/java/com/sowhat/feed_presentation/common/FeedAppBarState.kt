package com.sowhat.feed_presentation.common

import com.sowhat.designsystem.R
import com.sowhat.designsystem.common.DropdownItem

data class FeedAppBarState(
    var emotionDropdownItems: List<DropdownItem> = listOf(
        DropdownItem("전체", null),
        DropdownItem("행복", R.drawable.ic_happy_96),
        DropdownItem("슬픔", R.drawable.ic_sad_96),
        DropdownItem("놀람", R.drawable.ic_surprise_96),
        DropdownItem("화남", R.drawable.ic_angry_96),
    ),
    var currentEmotion: DropdownItem = emotionDropdownItems.first(),
    var isDropdownExpanded: Boolean = false,
    var tabItems: List<String> = listOf(
        "최근글", "인기글"
    ),
    var selectedTabItem: String = tabItems.first()
)