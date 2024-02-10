package com.sowhat.authentication_domain.use_case

import androidx.room.util.joinIntoString
import com.sowhat.authentication_domain.model.NewMember
import com.sowhat.authentication_domain.repository.AuthRepository
import com.sowhat.common.model.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostNewMemberUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        joinInfo: RequestBody,
        profileImage: MultipartBody.Part?
    ): Resource<NewMember> {
        return authRepository.signUp(
            joinInfo = joinInfo,
            profileImage = profileImage
        )
    }
}