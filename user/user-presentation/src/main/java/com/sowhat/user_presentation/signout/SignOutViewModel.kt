package com.sowhat.user_presentation.signout

import androidx.lifecycle.ViewModel
import com.sowhat.common.model.PostingEvent
import com.sowhat.common.model.SignOutEvent
import com.sowhat.user_presentation.common.SignOutUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignOutViewModel @Inject constructor(

) : ViewModel() {

    private var _uiState = MutableStateFlow(SignOutUiState())
    val uiState = _uiState.asStateFlow()

    private val signOutEventChannel = Channel<PostingEvent>()
    val signOutEvent = signOutEventChannel.receiveAsFlow()

    fun signOut() {

    }

    fun withdraw() {

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