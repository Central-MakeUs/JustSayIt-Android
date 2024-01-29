package com.sowhat.feed_presentation.common

import com.sowhat.designsystem.common.DropdownItem
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.TabItem

data class FeedAppBarState(
    var emotionItems: List<Mood> = Mood.values().toList(),
    var currentEmotion: Mood = Mood.values().first(),
    var isDropdownExpanded: Boolean = false,
    var tabItems: List<TabItem> = listOf(
        TabItem("최근글", "latest"),
        TabItem("인기글", "") // TODO api 나오면 정하기
    ),
    var selectedTabItem: TabItem = tabItems.first()
)