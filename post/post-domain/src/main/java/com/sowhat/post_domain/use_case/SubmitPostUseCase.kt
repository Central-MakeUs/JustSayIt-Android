package com.sowhat.post_domain.use_case

import com.sowhat.common.model.Resource
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.post_domain.repository.PostRepository
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SubmitPostUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val authRepository: AuthDataRepository,
) {
    suspend operator fun invoke(
        storyInfo: RequestBody,
        storyImg: List<MultipartBody.Part?>?
    ): Resource<Unit?> {
        val authData = authRepository.authData.first()
        val accessToken = authData.accessToken
//        val memberId = authData.memberId

        if (accessToken == null) {
            return Resource.Error(message = "로그인을 진행해야 게시글을 작성할 수 있습니다.")
        }

        return postRepository.submitPost(accessToken, storyInfo, storyImg)
    }
}