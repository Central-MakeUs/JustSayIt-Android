package com.practice.report_domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodayMood(
    @SerialName("memberName")
    val memberName: String,
    @SerialName("myMoodRecord")
    val myMoodRecord: List<MyMood>
)

@Serializable
data class MyMood(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("moodCode")
    val moodCode: String,
    @SerialName("moodId")
    val moodId: Int
)