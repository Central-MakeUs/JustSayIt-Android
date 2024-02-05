package com.sowhat.report_data.repository

import com.sowhat.database.entity.MyFeedEntity
import com.sowhat.report_data.model.MyFeedResponse
import com.sowhat.report_data.model.PostMood
import com.sowhat.report_data.model.TodayMoodDto
import com.sowhat.report_data.remote.ReportApi
import com.sowhat.report_domain.model.MyFeed
import com.sowhat.report_domain.model.MyMood
import com.sowhat.report_domain.model.TodayMood
import com.sowhat.report_domain.repository.ReportRepository
import com.sowhat.common.model.Resource
import com.sowhat.network.util.getHttpErrorResource
import com.sowhat.network.util.getIOErrorResource
import retrofit2.HttpException
import java.io.IOException

class ReportRepositoryImpl(
    private val reportApi: ReportApi
) : ReportRepository {
    override suspend fun getMyFeedList(
        accessToken: String,
        sortBy: String,
//        memberId: Long,
        emotionCode: String?,
        lastId: Long?,
        size: Int
    ): Resource<MyFeed> {
        val myFeedDto = reportApi.getMyFeedList(
            accessToken = accessToken,
            sortBy = sortBy,
//            memberId = memberId,
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

    override suspend fun getTodayMoodData(accessToken: String): Resource<TodayMood> = try {
        val todayMoodDto = reportApi.getTodayMoodData(accessToken)

        todayMoodDto.data?.let {
            Resource.Success(
                data = getTodayMood(it),
                code = todayMoodDto.code,
                message = todayMoodDto.message
            )
        } ?: Resource.Error(
            code = todayMoodDto.code,
            message = todayMoodDto.message,
        )
    } catch (e: HttpException) {
        getHttpErrorResource(e)
    } catch (e: IOException) {
        getIOErrorResource(e)
    }


    private fun getTodayMood(todayMoodDto: TodayMoodDto) = TodayMood(
        memberName = todayMoodDto.memberName,
        myMoodRecord = todayMoodDto.myMoodRecord.map { moodRecord ->
            MyMood(
                createdAt = moodRecord.createdAt,
                moodCode = moodRecord.moodCode,
                moodId = moodRecord.moodId
            )
        }
    )

    override suspend fun postNewMood(accessToken: String, mood: String): Resource<Unit?> = try {
        val newMood = PostMood(moodCode = mood)
        val result = reportApi.postNewMood(
            accessToken = accessToken,
            mood = newMood
        )

        if (result.isSuccess) {
            Resource.Success(
                data = result.data,
                code = result.code,
                message = result.message
            )
        } else {
            Resource.Error(
                code = result.code,
                message = result.message
            )
        }
    } catch (e: HttpException) {
        getHttpErrorResource(e)
    } catch (e: IOException) {
        getIOErrorResource(e)
    }
}