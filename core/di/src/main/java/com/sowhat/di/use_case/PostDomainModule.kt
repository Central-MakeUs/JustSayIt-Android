package com.sowhat.di.use_case

import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.authentication_domain.use_case.PostNewMemberUseCase
import com.sowhat.database.FeedDatabase
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.post_domain.repository.PostRepository
import com.sowhat.post_domain.use_case.EditPostUseCase
import com.sowhat.post_domain.use_case.GetFeedDataUseCase
import com.sowhat.post_domain.use_case.SubmitPostUseCase
import com.sowhat.post_domain.use_case.ValidateCurrentMoodUseCase
import com.sowhat.post_domain.use_case.ValidatePostImagesUseCase
import com.sowhat.post_domain.use_case.ValidatePostTextUseCase
import com.sowhat.post_domain.use_case.ValidateSympathyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostDomainModule {
    @Provides
    @Singleton
    fun provideSubmitPostUseCase(
        repository: PostRepository,
        authDataRepository: AuthDataRepository
    ): SubmitPostUseCase =
        SubmitPostUseCase(repository, authDataRepository)

    @Provides
    @Singleton
    fun provideEditPostUseCase(
        repository: PostRepository,
        authDataRepository: AuthDataRepository
    ): EditPostUseCase =
        EditPostUseCase(repository, authDataRepository)

    @Provides
    @Singleton
    fun provideValidateCurrentMoodUseCase(): ValidateCurrentMoodUseCase = ValidateCurrentMoodUseCase()

    @Provides
    @Singleton
    fun provideValidatePostImagesUseCase(): ValidatePostImagesUseCase = ValidatePostImagesUseCase()

    @Provides
    @Singleton
    fun provideValidatePostTextUseCase(): ValidatePostTextUseCase = ValidatePostTextUseCase()

    @Provides
    @Singleton
    fun provideValidateSympathyUseCase(): ValidateSympathyUseCase = ValidateSympathyUseCase()

    @Provides
    @Singleton
    fun provideGetFeedDataUseCase(
        feedDatabase: FeedDatabase
    ): GetFeedDataUseCase = GetFeedDataUseCase(feedDatabase = feedDatabase)
}