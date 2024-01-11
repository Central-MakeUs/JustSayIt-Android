package com.sowhat.network.di

import androidx.multidex.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sowhat.network.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor().apply {
            // level : BODY -> logs headers + bodies of request, response
            // NONE, BASIC, HEADERS, BODY
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(30, TimeUnit.SECONDS)
            // readTimeout : maximum time gap between 'arrivals' of two data packets when waiting for the server's response
            readTimeout(20, TimeUnit.SECONDS)
            // writeTimeout : maximum time gap between two data packets when 'sending' them to the server
            writeTimeout(25, TimeUnit.SECONDS)
        }.build()

        return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json {
            // specifies whether encounters of unknown properties in the input JSON should be ignored,
            // instead of exception(SerializationException)
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            // which library to use for "de"serialization(JSON -> Object)
            // kotlinx-serialization dependency
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}