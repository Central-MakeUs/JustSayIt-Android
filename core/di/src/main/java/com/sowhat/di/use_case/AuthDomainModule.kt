package com.sowhat.di.use_case

import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.authentication_domain.use_case.PostNewMemberUseCase
import com.sowhat.authentication_domain.use_case.UserSignInUseCase
import com.sowhat.authentication_domain.use_case.ValidateDayUseCase
import com.sowhat.authentication_domain.use_case.ValidateGenderUseCase
import com.sowhat.authentication_domain.use_case.ValidateMonthUseCase
import com.sowhat.authentication_domain.use_case.ValidateNicknameUseCase
import com.sowhat.authentication_domain.use_case.ValidateYearUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {
    @Provides
    @Singleton
    fun providePostNewMemberUseCase(repository: AuthRepository): PostNewMemberUseCase =
        PostNewMemberUseCase(authRepository = repository)

    @Provides
    @Singleton
    fun provideUserSignInUseCase(repository: AuthRepository): UserSignInUseCase =
        UserSignInUseCase(authRepository = repository)

    @Provides
    @Singleton
    fun provideValidateNicknameUseCase(): ValidateNicknameUseCase = ValidateNicknameUseCase()

    @Provides
    @Singleton
    fun provideValidateGenderUseCase(): ValidateGenderUseCase = ValidateGenderUseCase()

    @Provides
    @Singleton
    fun provideValidateYearUseCase(): ValidateYearUseCase = ValidateYearUseCase()

    @Provides
    @Singleton
    fun provideValidateMonthUseCase(): ValidateMonthUseCase = ValidateMonthUseCase()

    @Provides
    @Singleton
    fun provideValidateDayUseCase(): ValidateDayUseCase = ValidateDayUseCase()
}