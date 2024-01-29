package com.sowhat.di.repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.database.FeedDatabase
import com.practice.database.common.DATABASE_FEED
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideFeedDatabase(
        @ApplicationContext context: Context
    ): FeedDatabase = FeedDatabase.getInstance(context)
}