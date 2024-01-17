package com.sowhat.di.repository

import com.sowhat.authentication_data.remote.AuthApi
import com.sowhat.authentication_data.repository.AuthRepositoryImpl
import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.user_data.remote.UserApi
import com.sowhat.user_data.repository.UserRepositoryImpl
import com.sowhat.user_domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository =
        AuthRepositoryImpl(authApi)

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository =
        UserRepositoryImpl(userApi)
}