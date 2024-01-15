package com.sowhat.common.wrapper

sealed class UiEvent {
    object Loading : UiEvent()
    object Success : UiEvent()
    object NavigateUp : UiEvent()
    data class Error(val message: String) : UiEvent()
}
