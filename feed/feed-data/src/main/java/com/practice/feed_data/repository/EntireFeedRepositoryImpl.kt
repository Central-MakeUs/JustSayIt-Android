package com.practice.feed_data.repository

import com.practice.database.FeedDatabase
import com.practice.database.entity.EntireFeedEntity
import com.practice.feed_data.model.FeedResponse
import com.practice.feed_data.remote.FeedApi
import com.sowhat.common.model.Resource
import com.sowhat.feed_domain.model.EntireFeed
import com.sowhat.feed_domain.repository.EntireFeedRepository
import com.sowhat.network.util.getHttpErrorResource
import com.sowhat.network.util.getIOErrorResource
import retrofit2.HttpException
import java.io.IOException

class EntireFeedRepositoryImpl(
    private val feedApi: FeedApi,
    private val feedDatabase: FeedDatabase
) : EntireFeedRepository {
    override suspend fun getEntireFeedList(
        accessToken: String,
        sortBy: String,
        emotionCode: String?,
        lastId: Long?,
        size: Int
    ): Resource<EntireFeed> {
        val entireFeedDto = feedApi.getFeedData(
            accessToken = accessToken,
            sortBy = sortBy,
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

    override suspend fun reportFeed(
        accessToken: String,
        feedId: Long,
        reportCode: String
    ): Resource<Unit?> = try {

        val reportResult = feedApi.reportFeed(
            accessToken = accessToken,
            feedId = feedId,
            reportCode = reportCode
        )

        reportResult.data?.let {
            Resource.Success(
                data = it,
                code = reportResult.code,
                message = reportResult.message
            )
        } ?: Resource.Error(
            code = reportResult.code,
            message = reportResult.message
        )
    } catch (e: HttpException) {
        getHttpErrorResource(e)
    } catch (e: IOException) {
        getIOErrorResource(e)
    }

    override suspend fun blockUser(
        accessToken: String,
        blockedId: Long
    ): Resource<Unit?> = try {
        val blockResult = feedApi.blockUser(
            accessToken = accessToken,
            blockedId = blockedId
        )
        val dao = feedDatabase.entireFeedDao

        blockResult.data?.let {
            dao.deleteFeedItemByUserId(blockedId)

            Resource.Success(
                data = it,
                code = blockResult.code,
                message = blockResult.message
            )
        } ?: Resource.Error(
            code = blockResult.code,
            message = blockResult.message
        )
    } catch (e: HttpException) {
        getHttpErrorResource(e)
    } catch (e: IOException) {
        getIOErrorResource(e)
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