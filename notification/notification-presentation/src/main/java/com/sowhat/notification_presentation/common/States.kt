package com.sowhat.notification_presentation.common

import com.sowhat.database.entity.NotificationEntity

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationEntity> = emptyList()
)