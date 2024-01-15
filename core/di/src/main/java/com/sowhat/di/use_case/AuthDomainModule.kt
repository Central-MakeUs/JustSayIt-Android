package com.sowhat.di.use_case

import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.authentication_domain.use_case.PostNewMemberUseCase
import com.sowhat.authentication_domain.use_case.UserSignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {
    @Provides
    @Singleton
    fun providePostNewMemberUseCase(repository: AuthRepository): PostNewMemberUseCase =
        PostNewMemberUseCase(authRepository = repository)

    @Provides
    @Singleton
    fun provideUserSignInUseCase(repository: AuthRepository): UserSignInUseCase =
        UserSignInUseCase(authRepository = repository)
}