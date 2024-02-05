package com.sowhat.feed_data.di

import com.sowhat.feed_data.remote.FeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedModule {
    @Provides
    @Singleton
    fun provideFeedApi(
        retrofit: Retrofit
    ): FeedApi = retrofit.create(FeedApi::class.java)
}