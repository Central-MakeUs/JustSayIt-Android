package com.sowhat.report_presentation.common

import android.os.Parcelable
import com.sowhat.designsystem.common.Mood
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodayMoodItem(
    val mood: Mood?,
    val time: String
) : Parcelable
