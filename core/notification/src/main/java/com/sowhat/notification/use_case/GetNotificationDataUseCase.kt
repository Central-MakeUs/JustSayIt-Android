package com.sowhat.notification.use_case

import com.sowhat.common.model.FCMData
import com.sowhat.database.FeedDatabase
import com.sowhat.database.entity.NotificationEntity
import javax.inject.Inject

class GetNotificationDataUseCase @Inject constructor(
    private val feedDatabase: FeedDatabase
) {
    suspend operator fun invoke(): List<NotificationEntity> {
        val dao = feedDatabase.notificationDao
        return dao.getAllDataSynchronously()
    }
}