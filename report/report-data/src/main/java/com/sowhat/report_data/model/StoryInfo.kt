package com.sowhat.report_data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryInfo(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("emotionOfEmpathy")
    val emotionOfEmpathy: EmotionOfEmpathy,
    @SerialName("isMine")
    val isMine: Boolean,
    @SerialName("profileInfo")
    val profileInfo: ProfileInfo,
    @SerialName("storyId")
    val storyId: Long,
    @SerialName("storyMainContent")
    val storyMainContent: StoryMainContent,
    @SerialName("storyMetaInfo")
    val storyMetaInfo: StoryMetaInfo,
    @SerialName("storyUUID")
    val storyUUID: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("writerId")
    val writerId: Long
)

@Serializable
data class EmotionOfEmpathy(
    @SerialName("angryCount")
    val angryCount: Long,
    @SerialName("happinessCount")
    val happinessCount: Long,
    @SerialName("isAngrySelected")
    val isAngrySelected: Boolean,
    @SerialName("isHappinessSelected")
    val isHappinessSelected: Boolean,
    @SerialName("isSadnessSelected")
    val isSadnessSelected: Boolean,
    @SerialName("isSurprisedSelected")
    val isSurprisedSelected: Boolean,
    @SerialName("sadnessCount")
    val sadnessCount: Long,
    @SerialName("surprisedCount")
    val surprisedCount: Long,
    @SerialName("totalCount")
    val totalCount: Long
)

@Serializable
data class ProfileInfo(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImg")
    val profileImg: String
)

@Serializable
data class StoryMainContent(
    @SerialName("bodyText")
    val bodyText: String,
    @SerialName("photo")
    val photo: List<Photo>,
    @SerialName("writerEmotion")
    val writerEmotion: String
)

@Serializable
data class Photo(
    @SerialName("photoId")
    val photoId: Long,
    @SerialName("photoUrl")
    val photoUrl: String
)

@Serializable
data class StoryMetaInfo(
    @SerialName("isAnonymous")
    val isAnonymous: Boolean,
    @SerialName("isModified")
    val isModified: Boolean,
    @SerialName("isOpened")
    val isOpened: Boolean
)

