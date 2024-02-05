package com.sowhat.domain.repository

import com.sowhat.common.model.Resource

interface CommonRepository {
    suspend fun deleteFeed(
        accessToken: String,
        feedId: Long
    ): Resource<Unit?>
}