package com.practice.data.di

import com.practice.data.remote.CommonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    @Singleton
    fun provideCommonApi(
        retrofit: Retrofit
    ): CommonApi = retrofit.create(CommonApi::class.java)
}