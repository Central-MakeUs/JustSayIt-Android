package com.sowhat.di.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.datastore.AuthDataSerializer
import com.sowhat.datastore.DATASTORE_AUTH
import com.sowhat.datastore.model.AuthData
import com.sowhat.datastore.AuthDataRepositoryImpl
import com.sowhat.datastore.use_case.UpdateFcmTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// hilt in multimodule architecture
// https://developer.android.com/training/dependency-injection/hilt-multi-module
@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {
    @Singleton
    @Provides
    fun provideProtoAuthData(
        @ApplicationContext applicationContext: Context
    ): DataStore<AuthData> {
        return DataStoreFactory.create(
            serializer = AuthDataSerializer,
            produceFile = { applicationContext.dataStoreFile(DATASTORE_AUTH) },
            corruptionHandler = null,
        )
    }

    @Singleton
    @Provides
    fun provideAuthDataRepository(
        protoDataStore: DataStore<AuthData>
    ): AuthDataRepository {
        return AuthDataRepositoryImpl(protoDataStore)
    }

    @Provides
    @Singleton
    fun provideUpdateFcmTokenUseCase(
        authDataRepository: AuthDataRepository
    ): UpdateFcmTokenUseCase = UpdateFcmTokenUseCase(authDataRepository)
}