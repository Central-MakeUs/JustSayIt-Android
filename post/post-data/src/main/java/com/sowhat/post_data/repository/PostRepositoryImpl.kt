package com.sowhat.post_data.repository

import com.sowhat.post_data.remote.PostApi
import com.sowhat.common.model.Resource
import com.sowhat.common.util.UploadBody
import com.sowhat.network.util.getHttpErrorResource
import com.sowhat.network.util.getIOErrorResource
import com.sowhat.post_domain.repository.PostRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val postApi: PostApi
) : PostRepository {
    override suspend fun submitPost(
        accessToken: String,
//        memberId: Long,
        storyInfo: UploadBody,
        storyImg: List<MultipartBody.Part?>?
    ): Resource<Unit?> {
        return try {
            getSubmitPostResource(
                accessToken = accessToken,
//                memberId = memberId,
                storyInfo = storyInfo,
                storyImg = storyImg
            )
        } catch (e: HttpException) {
            getHttpErrorResource(e)
        } catch (e: IOException) {
            getIOErrorResource(e)
        }
    }

    private suspend fun getSubmitPostResource(
        accessToken: String,
//        memberId: Long,
        storyInfo: UploadBody,
        storyImg: List<MultipartBody.Part?>?
    ): Resource<Unit?> {
        val postResult = postApi.submitPost(
            accessToken = accessToken,
//            memberId = memberId,
            storyInfo = storyInfo,
            storyImg = storyImg
        )

        return if (postResult.isSuccess) {
            Resource.Success(
                data = null,
                code = postResult.code,
                message = postResult.message
            )
        } else Resource.Error(
            data = null,
            code = postResult.code,
            message = postResult.message
        )
    }

    override suspend fun editPost(
        accessToken: String,
        storyInfo: RequestBody,
        newImg: List<MultipartBody.Part?>?
    ): Resource<Unit?> = try {
        getEditPostResource(
            accessToken = accessToken,
            storyInfo = storyInfo,
            newImg = newImg
        )
    } catch (e: HttpException) {
        getHttpErrorResource(e)
    } catch (e: IOException) {
        getIOErrorResource(e)
    }

    private suspend fun getEditPostResource(
        accessToken: String,
        storyInfo: RequestBody,
        newImg: List<MultipartBody.Part?>?
    ): Resource<Unit?> {
        val postResult = postApi.editPost(
            accessToken = accessToken,
            storyInfo = storyInfo,
            newImg = newImg
        )

        return if (postResult.isSuccess) {
            Resource.Success(
                data = null,
                code = postResult.code,
                message = postResult.message
            )
        } else Resource.Error(
            data = null,
            code = postResult.code,
            message = postResult.message
        )
    }
}