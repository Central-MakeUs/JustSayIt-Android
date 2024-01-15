package com.sowhat.common.wrapper

sealed class SignInEvent {
    object NavigateToMain : SignInEvent()
    object NavigateToSignUp : SignInEvent()
    data class Error(val message: String) : SignInEvent()
}
