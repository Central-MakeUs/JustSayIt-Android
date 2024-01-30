package com.practice.report_data.repository

import androidx.paging.Pager
import com.practice.database.entity.MyFeedEntity
import com.practice.report_data.model.MyFeedResponse
import com.practice.report_data.remote.ReportApi
import com.practice.report_domain.model.MyFeed
import com.practice.report_domain.repository.ReportRepository
import com.sowhat.common.model.Resource

class ReportRepositoryImpl(
    private val reportApi: ReportApi
) : ReportRepository {
    override suspend fun getMyFeedList(
        accessToken: String,
        sortBy: String,
        memberId: Long,
        emotionCode: String?,
        lastId: Long?,
        size: Int
    ): Resource<MyFeed> {
        val myFeedDto = reportApi.getMyFeedList(
            accessToken = accessToken,
            sortBy = sortBy,
            memberId = memberId,
            emotionCode = emotionCode,
            lastId = lastId,
            size = size
        )

        return myFeedDto.data?.let {
            Resource.Success(
                code = myFeedDto.code,
                message = myFeedDto.message,
                data = MyFeed(
                    hasNext = it.hasNext,
                    feedItems = getMyFeedList(it)
                )
            )
        } ?: Resource.Error(
            code = myFeedDto.code,
            message = myFeedDto.message,
            data = null
        )
    }

    private fun getMyFeedList(myFeedResponse: MyFeedResponse) =
        myFeedResponse.storyInfo.map { feed ->
            MyFeedEntity(
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
                isOpened = feed.storyMetaInfo.isOpened
            )
        }
}