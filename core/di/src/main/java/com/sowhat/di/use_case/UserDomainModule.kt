package com.sowhat.di.use_case

import com.sowhat.datastore.AuthDataRepository
import com.sowhat.user_domain.repository.UserRepository
import com.sowhat.user_domain.use_case.GetUserInfoUseCase
import com.sowhat.user_domain.use_case.UpdateUserInfoUseCase
import com.sowhat.user_domain.use_case.WithdrawUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDomainModule {
    @Provides
    @Singleton
    fun provideGetUserInfoUseCase(
        userRepository: UserRepository,
        authDataRepository: AuthDataRepository
    ): GetUserInfoUseCase = GetUserInfoUseCase(
        userRepository = userRepository,
        authDatastore = authDataRepository
    )

    @Provides
    @Singleton
    fun provideUpdateUserInfoUseCase(
        userRepository: UserRepository,
        authDataRepository: AuthDataRepository
    ): UpdateUserInfoUseCase = UpdateUserInfoUseCase(
        userRepository = userRepository,
        authDatastore = authDataRepository
    )

    @Provides
    @Singleton
    fun provideWithdrawUserUseCase(
        userRepository: UserRepository,
        authDataRepository: AuthDataRepository
    ): WithdrawUserUseCase = WithdrawUserUseCase(
        userRepository = userRepository,
        authDatastore = authDataRepository
    )
}