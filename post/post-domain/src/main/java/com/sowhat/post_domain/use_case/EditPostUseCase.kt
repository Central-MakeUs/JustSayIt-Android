package com.sowhat.post_domain.use_case

import com.sowhat.common.model.Resource
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.post_domain.repository.PostRepository
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class EditPostUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val authRepository: AuthDataRepository,
) {
    suspend operator fun invoke(
        storyInfo: RequestBody,
        newImg: List<MultipartBody.Part?>?
    ): Resource<Unit?> {
        val authData = authRepository.authData.first()
        val accessToken = authData.accessToken
            ?: return Resource.Error(message = "로그인을 진행해야 게시글을 작성할 수 있습니다.")

        return postRepository.editPost(accessToken, storyInfo, newImg)
    }
}