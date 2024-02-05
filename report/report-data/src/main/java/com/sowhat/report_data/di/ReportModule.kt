package com.sowhat.report_data.di

import com.sowhat.report_data.remote.ReportApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReportModule {
    @Provides
    @Singleton
    fun provideReportApi(
        retrofit: Retrofit
    ): ReportApi = retrofit.create(ReportApi::class.java)
}