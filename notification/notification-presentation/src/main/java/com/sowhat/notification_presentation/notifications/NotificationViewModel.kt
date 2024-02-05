package com.sowhat.notification_presentation.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.database.entity.NotificationEntity
import com.sowhat.notification.use_case.GetNotificationDataUseCase
import com.sowhat.notification_presentation.common.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationDataUseCase: GetNotificationDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState = _uiState.asStateFlow()

    fun getNotificationData() {
        viewModelScope.launch {
            _uiState.update {
                uiState.value.copy(isLoading = true)
            }
            val data = getNotificationDataUseCase()
            Log.i("NotificationScreen", "getNotificationData: $data")
            _uiState.update {
                uiState.value.copy(notifications = data, isLoading = false)
            }
        }
    }
}