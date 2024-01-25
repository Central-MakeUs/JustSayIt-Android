package com.sowhat.user_presentation.signout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.common.model.PostingEvent
import com.sowhat.common.model.Resource
import com.sowhat.common.model.SignOutEvent
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.user_domain.use_case.WithdrawUserUseCase
import com.sowhat.user_presentation.common.SignOutUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignOutViewModel @Inject constructor(
    private val withdrawUserUseCase: WithdrawUserUseCase,
    private val authDataRepository: AuthDataRepository
) : ViewModel() {

    private var _uiState = MutableStateFlow(SignOutUiState())
    val uiState = _uiState.asStateFlow()

    private val signOutEventChannel = Channel<PostingEvent>()
    val signOutEvent = signOutEventChannel.receiveAsFlow()

    fun signOut() {
        viewModelScope.launch {
            _uiState.update {
                uiState.value.copy(
                    showSignOut = false,
                )
            }

            authDataRepository.updateForSignOut()
            signOutEventChannel.send(PostingEvent.NavigateUp)
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            _uiState.update {
                uiState.value.copy(
                    isLoading = true,
                    showWithdraw = false
                )
            }

            val result = withdrawUserUseCase()

            when (result) {
                is Resource.Success -> {
                    _uiState.update {
                        uiState.value.copy(
                            isLoading = false,
                        )
                    }

                    authDataRepository.resetData()
                    signOutEventChannel.send(PostingEvent.NavigateUp)
                }
                is Resource.Error -> {
                    _uiState.update {
                        uiState.value.copy(
                            isLoading = false,
                        )
                    }

                    signOutEventChannel.send(PostingEvent.Error(message = result.message ?: ""))
                }
            }
        }
    }

    fun onEvent(event: SignOutEvent) {
        when (event) {
            is SignOutEvent.SignOutVisibilityChanged -> {
                // update : for mutablestateflow -> concurrently update
                _uiState.update {
                    uiState.value.copy(showSignOut = event.isVisible)
                }
            }
            is SignOutEvent.WithdrawVisibilityChanged -> {
                _uiState.update {
                    uiState.value.copy(showWithdraw = event.isVisible)
                }
            }
        }
    }
}