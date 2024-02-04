package com.practice.report_data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodayMoodDto(
    @SerialName("memberName")
    val memberName: String,
    @SerialName("myMoodRecord")
    val myMoodRecord: List<MyMoodRecord>
)

@Serializable
data class MyMoodRecord(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("moodCode")
    val moodCode: String,
    @SerialName("moodId")
    val moodId: Int
)