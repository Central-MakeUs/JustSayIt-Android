package com.sowhat.authentication_presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.authentication_domain.model.SignIn
import com.sowhat.authentication_domain.use_case.UserSignInUseCase
import com.sowhat.authentication_presentation.common.Platform
import com.sowhat.common.model.Resource
import com.sowhat.common.model.SignInEvent
import com.sowhat.common.model.SplashEvent
import com.sowhat.datastore.AuthDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val signInUseCase: UserSignInUseCase,
    private val authDataStore: AuthDataRepository
) : ViewModel() {
    private val _uiEvent = Channel<SplashEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val tokens = viewModelScope.async {
        authDataStore.authData.first()
    }

    init {
        signIn()
    }

    private fun signIn() = viewModelScope.launch {
        val accessToken = tokens.await().accessToken
        val platformToken = tokens.await().platformToken
        Log.i("OnboardingScreen", "platformToken : $platformToken")

        if (accessToken == null || platformToken.isNullOrBlank()) {
            Log.i("OnboardingScreen", "platformToken is null : $platformToken")
            _uiEvent.send(SplashEvent.NavigateToSignIn)
            return@launch
        }

        // 만약 액세스 토큰이 없을 때 갱신되었을 수 있기 때문에 여기에 새로 선언
        val signInData = signInUseCase(platformToken)

        consumeResources(signInData)
    }

    private suspend fun consumeResources(signInData: Resource<SignIn>) {
        when (signInData) {
            is Resource.Success -> {
                val data = signInData.data
                consumeSuccessResources(data)
            }
            is Resource.Error -> {
                consumeErrorResources(signInData)
            }
        }
    }

    private suspend fun consumeErrorResources(signInData: Resource<SignIn>) {
        _uiEvent.send(
            SplashEvent.Error(message = signInData.message ?: "예상치 못한 오류가 발생했습니다.")
        )
    }

    private suspend fun consumeSuccessResources(
        data: SignIn?
    ) {
        if (data?.memberId != null && data.isJoined) {
            data.accessToken?.let {
                authDataStore.updateAccessToken(it)
            }
            authDataStore.updateMemberId(data.memberId!!)
            _uiEvent.send(SplashEvent.NavigateToMain)
            return
        }

        if (data?.isJoined == false) {
            _uiEvent.send(SplashEvent.NavigateToSignIn)
            return
        }

        _uiEvent.send(SplashEvent.Error(message = "예상치 못한 오류가 발생했습니다."))
    }
}