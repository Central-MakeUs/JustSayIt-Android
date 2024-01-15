package com.sowhat.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.authentication_domain.model.SignIn
import com.sowhat.authentication_domain.use_case.UserSignInUseCase
import com.sowhat.common.wrapper.Resource
import com.sowhat.common.wrapper.SignInEvent
import com.sowhat.common.wrapper.UiState
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.presentation.common.Platform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val signInUseCase: UserSignInUseCase,
    private val authDataStore: AuthDataRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<SignIn>> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState<SignIn>>
        get() = _uiState

    private val _uiEvent = Channel<SignInEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val tokens = viewModelScope.async {
        authDataStore.authData.first()
    }

    fun signIn(
        platform: Platform,
        platformToken: String
    ) = viewModelScope.launch {
        _uiState.value.copy(isLoading = true)

        authDataStore.apply {
            updatePlatform(platform = platform.title)
            updatePlatformToken(platformToken = platformToken)
        }

        val platformToken = tokens.await().platformToken
        val signInData = signInUseCase(platformToken)

        consumeResources(signInData)
    }

    private suspend fun consumeResources(signInData: Resource<SignIn>) {
        when (signInData) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                val data = signInData.data
                if (data?.memberId != null && data?.isJoined == true) {
                    data.accessToken?.let {
                        authDataStore.updateAccessToken(it)
                    }
                    _uiEvent.send(SignInEvent.NavigateToMain)
                    _uiState.value.copy(isLoading = false, data = signInData.data)
                    return
                }

                if (data?.isJoined == false) {
                    _uiEvent.send(SignInEvent.NavigateToSignUp)
                    _uiState.value.copy(isLoading = false, data = signInData.data)
                    return
                }

                _uiState.value.copy(isLoading = false, data = signInData.data)
                _uiEvent.send(SignInEvent.Error(message = "서버로부터 토큰을 받아올 수 없습니다."))
            }

            is Resource.Error -> {
                _uiState.value.copy(isLoading = false, data = signInData.data)
                _uiEvent.send(
                    SignInEvent.Error(message = signInData.message ?: "예상치 못한 오류가 발생했습니다.")
                )
            }
        }
    }
}