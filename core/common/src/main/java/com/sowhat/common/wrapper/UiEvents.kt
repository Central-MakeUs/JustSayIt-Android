package com.sowhat.common.wrapper

import android.graphics.Bitmap
import okhttp3.MultipartBody
import java.io.File

sealed class SignInEvent {
    object NavigateToMain : SignInEvent()
    object NavigateToSignUp : SignInEvent()
    data class Error(val message: String) : SignInEvent()
}

sealed class SignUpEvent {
    object NavigateToMain : SignUpEvent()
    data class Error(val message: String) : SignUpEvent()
}

sealed class RegistrationFormEvent {
    data class ProfileChanged(val image: MultipartBody.Part?) : RegistrationFormEvent()
    data class NicknameChanged(val nickname: String) : RegistrationFormEvent()
    data class GenderChanged(val gender: String) : RegistrationFormEvent()
    data class YearChanged(val year: String) : RegistrationFormEvent()
    data class MonthChanged(val month: String) : RegistrationFormEvent()
    data class DayChanged(val day: String) : RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}