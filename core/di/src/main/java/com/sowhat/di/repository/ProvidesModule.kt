package com.sowhat.di.repository

import com.sowhat.data.remote.CommonApi
import com.sowhat.data.repository.CommonRepositoryImpl
import com.sowhat.database.FeedDatabase
import com.sowhat.domain.repository.CommonRepository
import com.sowhat.feed_data.remote.FeedApi
import com.sowhat.feed_data.repository.EntireFeedRepositoryImpl
import com.sowhat.post_data.remote.PostApi
import com.sowhat.post_data.repository.PostRepositoryImpl
import com.sowhat.report_data.remote.ReportApi
import com.sowhat.report_data.repository.ReportRepositoryImpl
import com.sowhat.report_domain.repository.ReportRepository
import com.sowhat.authentication_data.remote.AuthApi
import com.sowhat.authentication_data.repository.AuthRepositoryImpl
import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.feed_domain.repository.EntireFeedRepository
import com.sowhat.post_domain.repository.PostRepository
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

    @Provides
    @Singleton
    fun providePostRepository(postApi: PostApi): PostRepository =
        PostRepositoryImpl(postApi)

    @Provides
    @Singleton
    fun provideReportRepository(reportApi: ReportApi): ReportRepository =
        ReportRepositoryImpl(reportApi)

    @Provides
    @Singleton
    fun provideFeedRepository(
        feedApi: FeedApi,
        feedDatabase: FeedDatabase
    ): EntireFeedRepository =
        EntireFeedRepositoryImpl(feedApi, feedDatabase)

    @Provides
    @Singleton
    fun provideCommonRepository(
        commonApi: CommonApi,
        feedDatabase: FeedDatabase
    ): CommonRepository = CommonRepositoryImpl(feedDatabase, commonApi)
}