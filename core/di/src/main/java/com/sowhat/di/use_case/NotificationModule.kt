package com.sowhat.di.use_case

import com.sowhat.database.FeedDatabase
import com.sowhat.notification.use_case.GetNotificationDataUseCase
import com.sowhat.notification.use_case.InsertNotificationDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    @Singleton
    fun getInsertNotificationDataUseCase(
        feedDatabase: FeedDatabase
    ): InsertNotificationDataUseCase = InsertNotificationDataUseCase(feedDatabase)

    @Provides
    @Singleton
    fun getNotificationDataUseCase(
        feedDatabase: FeedDatabase
    ): GetNotificationDataUseCase = GetNotificationDataUseCase(feedDatabase)
}