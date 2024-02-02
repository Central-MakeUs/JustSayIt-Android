package com.practice.feed_data.repository

import com.practice.database.entity.EntireFeedEntity
import com.practice.feed_data.model.FeedResponse
import com.practice.feed_data.remote.FeedApi
import com.sowhat.common.model.Resource
import com.sowhat.feed_domain.model.EntireFeed
import com.sowhat.feed_domain.repository.EntireFeedRepository

class EntireFeedRepositoryImpl(
    private val feedApi: FeedApi
) : EntireFeedRepository {
    override suspend fun getEntireFeedList(
        accessToken: String,
        sortBy: String,
        memberId: Long,
        emotionCode: String?,
        lastId: Long?,
        size: Int
    ): Resource<EntireFeed> {
        val entireFeedDto = feedApi.getFeedData(
            accessToken = accessToken,
            sortBy = sortBy,
            memberId = memberId,
            emotionCode = emotionCode,
            lastId = lastId,
            size = size
        )

        return entireFeedDto.data?.let {
            Resource.Success(
                code = entireFeedDto.code,
                message = entireFeedDto.message,
                data = EntireFeed(
                    hasNext = it.hasNext,
                    feedItems = getFeedList(it)
                )
            )
        } ?: Resource.Error(
            code = entireFeedDto.code,
            message = entireFeedDto.message,
            data = null
        )
    }

    private fun getFeedList(feedResponse: FeedResponse) =
        feedResponse.storyInfo.map { feed ->
            EntireFeedEntity(
                storyUUID = feed.storyUUID,
                storyId = feed.storyId,
                createdAt = feed.createdAt,
                updatedAt = feed.updatedAt,
                writerId = feed.writerId,
                totalCount = feed.emotionOfEmpathy.totalCount,
                happinessCount = feed.emotionOfEmpathy.happinessCount,
                sadnessCount = feed.emotionOfEmpathy.sadnessCount,
                surprisedCount = feed.emotionOfEmpathy.surprisedCount,
                angryCount = feed.emotionOfEmpathy.angryCount,
                isHappinessSelected = feed.emotionOfEmpathy.isHappinessSelected,
                isSadnessSelected = feed.emotionOfEmpathy.isSadnessSelected,
                isSurprisedSelected = feed.emotionOfEmpathy.isSurprisedSelected,
                isAngrySelected = feed.emotionOfEmpathy.isAngrySelected,
                nickname = feed.profileInfo.nickname,
                profileImg = feed.profileInfo.profileImg,
                bodyText = feed.storyMainContent.bodyText,
                photo = feed.storyMainContent.photo.map { it.photoUrl },
                writerEmotion = feed.storyMainContent.writerEmotion,
                isAnonymous = feed.storyMetaInfo.isAnonymous,
                isModified = feed.storyMetaInfo.isModified,
                isOpened = feed.storyMetaInfo.isOpened,
                selectedEmotionCode = feed.resultOfEmpathize.emotionCode,
                isOwner = feed.isMine
            )
        }
}