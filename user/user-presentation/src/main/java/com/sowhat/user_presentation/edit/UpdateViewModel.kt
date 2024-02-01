package com.sowhat.user_presentation.edit

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.common.model.Resource
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
//    var newImageUri by mutableStateOf<Uri?>(null)
//    var dropdown by mutableStateOf(false)

    private var _updateInfoUiState: MutableStateFlow<UiState<Unit?>> = MutableStateFlow(UiState())
    val updateInfoUiState: StateFlow<UiState<Unit?>> = _updateInfoUiState

    var formState by mutableStateOf(UpdateFormState())
        private set

    val isValid by derivedStateOf {
        (formState.nickname.length in (2..12) && formState.isNameChanged)
            || (formState.newImageUri == null && formState.isDefault)
            || formState.isProfileChanged
    }

    private val isFormValid by derivedStateOf {
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
            is UpdateFormEvent.DropdownVisibilityChanged -> {
                formState = formState.copy(isPopupMenuVisible = event.isVisible)
            }
            is UpdateFormEvent.ProfileUriChanged -> {
                formState = formState.copy(newImageUri = event.uri)
            }
            is UpdateFormEvent.IsProfileDefault -> {
                formState = formState.copy(isDefault = event.isDefault)
            }
            is UpdateFormEvent.IsNameChanged -> {
                formState = formState.copy(isNameChanged = event.isNameChanged)
            }

            is UpdateFormEvent.IsProfileChanged -> {
                formState = formState.copy(isProfileChanged = event.isChanged)
            }

            is UpdateFormEvent.NicknamePostDataChanged -> {
                formState = formState.copy(nicknamePostData = event.postData)
            }
        }

        Log.i("UpdateScreen", "onEvent: $formState")
    }

    fun updateUserInfo() {
        viewModelScope.launch {
            _updateInfoUiState.value = _updateInfoUiState.value.copy(isLoading = true)
            if (!isFormValid) {
                _updateInfoUiState.value = _updateInfoUiState.value.copy(
                    isLoading = false,
                    data = null
                )
                updateEventChannel.send(UpdateEvent.Error("변경 사항이 없습니다."))
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