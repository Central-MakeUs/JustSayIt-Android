package com.sowhat.di.use_case

import com.sowhat.domain.repository.CommonRepository
import com.sowhat.domain.use_case.DeleteFeedUseCase
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