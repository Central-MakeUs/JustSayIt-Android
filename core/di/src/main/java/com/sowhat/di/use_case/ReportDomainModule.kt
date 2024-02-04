package com.sowhat.di.use_case

import com.practice.database.FeedDatabase
import com.practice.report_data.remote.ReportApi
import com.practice.report_domain.repository.ReportRepository
import com.practice.report_domain.use_case.GetMyFeedUseCase
import com.practice.report_domain.use_case.GetTodayMoodDataUseCase
import com.practice.report_domain.use_case.PostNewMoodUseCase
import com.sowhat.datastore.AuthDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReportDomainModule {
    @Provides
    @Singleton
    fun provideGetMyFeedUseCase(
        reportRepository: ReportRepository,
        authDataRepository: AuthDataRepository,
        feedDatabase: FeedDatabase
    ): GetMyFeedUseCase = GetMyFeedUseCase(
        reportRepository,
        authDataRepository,
        feedDatabase
    )

    @Provides
    @Singleton
    fun provideGetTodayMoodDataUseCase(
        authDataRepository: AuthDataRepository,
        reportRepository: ReportRepository
    ): GetTodayMoodDataUseCase = GetTodayMoodDataUseCase(
        authDataRepository = authDataRepository,
        reportRepository = reportRepository
    )

    @Provides
    @Singleton
    fun providePostNewMoodUseCase(
        authDataRepository: AuthDataRepository,
        reportRepository: ReportRepository
    ): PostNewMoodUseCase = PostNewMoodUseCase(
        authDataRepository = authDataRepository,
        reportRepository = reportRepository
    )
}