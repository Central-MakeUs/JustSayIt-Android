package com.sowhat.notification.use_case

import com.sowhat.common.model.FCMData
import com.sowhat.database.FeedDatabase
import com.sowhat.database.entity.NotificationEntity
import javax.inject.Inject

class InsertNotificationDataUseCase @Inject constructor(
    private val feedDatabase: FeedDatabase
) {
    suspend operator fun invoke(fcmData: FCMData?) {
        fcmData?.let {
            val dao = feedDatabase.notificationDao
            dao.addNotificationItem(
                NotificationEntity(
                    title = fcmData.title,
                    body = fcmData.body,
                    targetCategory = fcmData.targetCategory,
                    targetData = fcmData.targetData,
                    date = fcmData.date
                )
            )
        }
    }
}