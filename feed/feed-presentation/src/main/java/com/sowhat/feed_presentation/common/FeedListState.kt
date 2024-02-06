package com.sowhat.feed_presentation.common

import android.os.Parcelable
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.TabItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedListState(
    val isImageDialogVisible: Boolean = false,
    val imageUrl: String? = null,
    var emotionItems: List<Mood> = Mood.values().toList(),
    var currentEmotion: Mood = Mood.values().first(),
    var isDropdownExpanded: Boolean = false,
    var tabItems: List<TabItem> = listOf(
        TabItem("최근글", "latest"),
        TabItem("인기글", "") // TODO api 나오면 정하기
    ),
    var selectedTabItem: TabItem = tabItems.first(),
    val isDeleteDialogVisible: Boolean = false,
    val isReportDialogVisible: Boolean = false,
    val isBlockDialogVisible: Boolean = false,
    val targetId: Long? = null,
    val reportPostData: String? = null
) : Parcelable