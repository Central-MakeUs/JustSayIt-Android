package com.practice.post_data.di

import com.practice.post_data.remote.PostApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): PostApi =
        retrofit.create(PostApi::class.java)

}