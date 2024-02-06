package com.sowhat.post_domain.use_case

import com.sowhat.common.navigation.HOME
import com.sowhat.common.navigation.MY
import com.sowhat.database.FeedDatabase
import com.sowhat.database.entity.EntireFeedEntity
import com.sowhat.database.entity.MyFeedEntity
import java.io.IOException
import javax.inject.Inject

class GetFeedDataUseCase @Inject constructor(
    private val feedDatabase: FeedDatabase
) {
    suspend operator fun invoke(feedId: Long, from: String): MyFeedEntity? {
        return try {
            when (from) {
                HOME -> {
                    val feedItem = feedDatabase.entireFeedDao.getFeedItemByFeedId(feedId)
                    getMyFeedEntity(feedItem)
                }
                MY -> feedDatabase.myFeedDao.getFeedItemByFeedId(feedId)
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun getMyFeedEntity(feedItem: EntireFeedEntity) = MyFeedEntity(
        storyUUID = feedItem.storyUUID,
        storyId = feedItem.storyId,
        createdAt = feedItem.createdAt,
        updatedAt = feedItem.updatedAt,
        writerId = feedItem.writerId,
        totalCount = feedItem.totalCount,
        happinessCount = feedItem.happinessCount,
        sadnessCount = feedItem.sadnessCount,
        surprisedCount = feedItem.surprisedCount,
        angryCount = feedItem.angryCount,
        isHappinessSelected = feedItem.isHappinessSelected,
        isSadnessSelected = feedItem.isSadnessSelected,
        isSurprisedSelected = feedItem.isSurprisedSelected,
        isAngrySelected = feedItem.isAngrySelected,
        nickname = feedItem.nickname,
        profileImg = feedItem.profileImg,
        bodyText = feedItem.bodyText,
        photo = feedItem.photo,
        photoId = feedItem.photoId,
        writerEmotion = feedItem.writerEmotion,
        isAnonymous = feedItem.isAnonymous,
        isModified = feedItem.isModified,
        isOpened = feedItem.isOpened
    )
}