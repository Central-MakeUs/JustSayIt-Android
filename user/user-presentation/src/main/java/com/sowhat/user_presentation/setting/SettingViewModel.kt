package com.sowhat.user_presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.common.model.Resource
import com.sowhat.common.model.UiState
import com.sowhat.user_domain.model.UserInfoDomain
import com.sowhat.user_domain.use_case.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<UserInfoDomain>>(UiState())
    val uiState: StateFlow<UiState<UserInfoDomain>> = _uiState

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val userInfo = getUserInfoUseCase()
            updateUiState(userInfo)
        }
    }

    private fun updateUiState(userInfo: Resource<UserInfoDomain>) {
        when (userInfo) {
            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    data = getRefinedData(userInfo)
                )
            }

            is Resource.Error -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = userInfo.message
                )
            }
        }
    }

    private fun getRefinedData(userInfo: Resource<UserInfoDomain>) =
        userInfo.data?.personalInfo?.birth?.replace("-", ". ")?.let {
            userInfo.data?.personalInfo?.copy(
                gender = when (userInfo.data?.personalInfo?.gender) {
                    MALE -> "남"
                    FEMALE -> "여"
                    else -> ""
                },
                birth = it
            )?.let { personalInfo ->
                userInfo.data?.copy(
                    personalInfo = personalInfo,
                )
            }
        }


    companion object {
        private const val MALE = "MALE"
        private const val FEMALE = "FEMALE"
    }
}