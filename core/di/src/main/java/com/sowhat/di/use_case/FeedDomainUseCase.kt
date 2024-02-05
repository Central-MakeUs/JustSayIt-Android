package com.sowhat.di.use_case

import com.sowhat.database.FeedDatabase
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.feed_domain.repository.EntireFeedRepository
import com.sowhat.feed_domain.use_case.BlockUserUseCase
import com.sowhat.feed_domain.use_case.CancelEmpathyUseCase
import com.sowhat.feed_domain.use_case.GetEntireFeedUseCase
import com.sowhat.feed_domain.use_case.PostEmpathyUseCase
import com.sowhat.feed_domain.use_case.ReportFeedUseCase
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

    @Provides
    @Singleton
    fun provideReportFeedUseCase(
        entireFeedRepository: EntireFeedRepository,
        authDataRepository: AuthDataRepository,
    ) = ReportFeedUseCase(
        entireFeedRepository = entireFeedRepository,
        authDataRepository = authDataRepository
    )

    @Provides
    @Singleton
    fun provideBlockUserUseCase(
        entireFeedRepository: EntireFeedRepository,
        authDataRepository: AuthDataRepository,
        feedDatabase: FeedDatabase
    ) = BlockUserUseCase(
        entireFeedRepository = entireFeedRepository,
        authDataRepository = authDataRepository,
        feedDatabase = feedDatabase
    )

    @Provides
    @Singleton
    fun providePostEmpathyUseCase(
        entireFeedRepository: EntireFeedRepository,
        authDataRepository: AuthDataRepository,
    ): PostEmpathyUseCase = PostEmpathyUseCase(
        entireFeedRepository = entireFeedRepository,
        authDataRepository = authDataRepository
    )

    @Provides
    @Singleton
    fun provideCancelEmpathyUseCase(
        entireFeedRepository: EntireFeedRepository,
        authDataRepository: AuthDataRepository,
    ): CancelEmpathyUseCase = CancelEmpathyUseCase(
        entireFeedRepository = entireFeedRepository,
        authDataRepository = authDataRepository
    )
}