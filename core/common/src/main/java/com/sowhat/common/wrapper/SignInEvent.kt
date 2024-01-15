package com.sowhat.common.wrapper

sealed class SignInEvent {
    object Success : SignInEvent()
    data class Error(val message: String) : SignInEvent()
}
