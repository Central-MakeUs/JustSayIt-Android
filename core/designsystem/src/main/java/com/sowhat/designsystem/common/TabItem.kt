package com.sowhat.designsystem.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TabItem(
    val title: String,
    val postData: String
) : Parcelable

val myPageSortTabItems = listOf(
    TabItem("최신순", "latest"),
    TabItem("오래된순", "oldest")
)
