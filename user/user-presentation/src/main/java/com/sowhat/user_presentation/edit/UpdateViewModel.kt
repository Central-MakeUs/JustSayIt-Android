package com.sowhat.user_presentation.edit

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.common.model.Resource
import com.sowhat.common.model.SignUpEvent
import com.sowhat.common.model.UiState
import com.sowhat.common.model.UpdateEvent
import com.sowhat.common.model.UpdateFormState
import com.sowhat.common.model.UpdateFormEvent
import com.sowhat.network.util.getRequestBody
import com.sowhat.user_domain.use_case.UpdateUserInfoUseCase
import com.sowhat.user_presentation.common.UpdateRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
) : ViewModel() {
    var profileUrl by mutableStateOf("https://github.com/kmkim2689/Android-Wiki/assets/101035437/88d7b249-ad72-4be9-8d79-38dc942e0a7f")
    var userName by mutableStateOf("케이엠")
    var newUserName by mutableStateOf("")
    var hasImage by mutableStateOf(false)
    var newImageUri by mutableStateOf<Uri?>(null)
    val isValid by derivedStateOf {
        userName.length in (2..12) || (newImageUri != null && hasImage)
    }
    var dropdown by mutableStateOf(false)

    private var _updateInfoUiState: MutableStateFlow<UiState<Unit?>> = MutableStateFlow(UiState())
    val updateInfoUiState: StateFlow<UiState<Unit?>> = _updateInfoUiState

    var formState by mutableStateOf(UpdateFormState())
        private set

    var imageUri by mutableStateOf<Uri?>(null)

    val isFormValid by derivedStateOf {
        formState.isNicknameValid
    }

    private val updateEventChannel = Channel<UpdateEvent>()
    val updateEvent = updateEventChannel.receiveAsFlow()

    fun onEvent(event: UpdateFormEvent) {
        when (event) {
            is UpdateFormEvent.ProfileChanged -> {
                formState = formState.copy(profileImage = event.image)
            }
            is UpdateFormEvent.NicknameChanged -> {
                formState = formState.copy(nickname = event.nickname)
            }
            is UpdateFormEvent.Submit -> {
                updateUserInfo()
            }
        }
    }

    private fun updateUserInfo() {
        viewModelScope.launch {
            _updateInfoUiState.value = _updateInfoUiState.value.copy(isLoading = true)
            if (!isFormValid) {
                _updateInfoUiState.value = _updateInfoUiState.value.copy(
                    isLoading = false,
                    data = null
                )
                updateEventChannel.send(UpdateEvent.Error("조건을 만족하지 못하였습니다."))
                return@launch
            }

            val requestBody = UpdateRequest(
                nickname = formState.nickname,
            )

            Log.i("UserConfigScreen", requestBody.toString())

            val result = updateUserInfoUseCase(
                editInfo = getRequestBody(requestBody),
                profileImage = formState.profileImage
            )

            when (result) {
                is Resource.Success -> {
                    _updateInfoUiState.value = _updateInfoUiState.value.copy(
                        isLoading = true,
                        data = result.data
                    )
                    updateEventChannel.send(UpdateEvent.NavigateUp)
                }
                is Resource.Error -> {
                    _updateInfoUiState.value = _updateInfoUiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                    updateEventChannel.send(UpdateEvent.Error(result.message ?: "예상치 못한 오류가 발생했습니다."))
                }
            }
        }
    }
}