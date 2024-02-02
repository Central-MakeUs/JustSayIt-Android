package com.sowhat.di.use_case

import com.practice.database.FeedDatabase
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.feed_domain.repository.EntireFeedRepository
import com.sowhat.feed_domain.use_case.GetEntireFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedDomainUseCase {
    @Provides
    @Singleton
    fun provideGetEntireFeedUseCase(
        entireFeedRepository: EntireFeedRepository,
        authDataRepository: AuthDataRepository,
        feedDatabase: FeedDatabase
    ) = GetEntireFeedUseCase(
        entireFeedRepository = entireFeedRepository,
        authDataRepository = authDataRepository,
        feedDatabase = feedDatabase
    )
}