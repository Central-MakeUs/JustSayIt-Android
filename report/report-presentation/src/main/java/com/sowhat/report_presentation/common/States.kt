package com.sowhat.report_presentation.common

import android.os.Parcelable
import com.sowhat.designsystem.common.Mood
import com.sowhat.designsystem.common.TabItem
import com.sowhat.designsystem.common.myPageSortTabItems
import kotlinx.parcelize.Parcelize

const val SORT_OLDEST = "oldest"
const val SORT_LATEST = "latest"

@Parcelize
data class MyFeedUiState(
    val lastId: Long? = null,
//    val sortBy: String = myPageSortTabItems.first().title,
//    val sortPostData: String = myPageSortTabItems.first().postData,
    val hasNext: Boolean = true,
    val emotion: Mood = Mood.values().first(),
    val isDropdownOpen: Boolean = false,
    val sortByItems: List<TabItem> = myPageSortTabItems,
    val sortBy: TabItem = myPageSortTabItems.first()
) : Parcelable // id("kotlin-parcelize")를 gradle에 선언해야 활용 가능
