package com.sowhat.di.use_case

import com.practice.data.remote.CommonApi
import com.practice.database.FeedDatabase
import com.practice.domain.repository.CommonRepository
import com.practice.domain.use_case.DeleteFeedUseCase
import com.sowhat.datastore.AuthDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonDomainModule {
    @Provides
    @Singleton
    fun provideDeleteFeedUseCase(
        authDataRepository: AuthDataRepository,
        commonRepository: CommonRepository
    ): DeleteFeedUseCase = DeleteFeedUseCase(authDataRepository, commonRepository)
}