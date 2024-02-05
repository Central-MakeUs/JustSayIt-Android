package com.sowhat.common.model

import android.net.Uri
import okhttp3.MultipartBody

sealed class SplashEvent {
    object NavigateToMain : SplashEvent()
    object NavigateToSignIn : SplashEvent()
    data class Error(val message: String) : SplashEvent()
}

sealed class SignInEvent {
    object NavigateToMain : SignInEvent()
    object NavigateToSignUp : SignInEvent()
    data class Error(val message: String) : SignInEvent()
}

sealed class SignUpEvent {
    object NavigateToMain : SignUpEvent()
    data class Error(val message: String) : SignUpEvent()
}

sealed class UpdateEvent {
    object NavigateUp : UpdateEvent()
    data class Error(val message: String) : UpdateEvent()
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


sealed class PostingEvent {
    object NavigateUp : PostingEvent()
    data class Error(val message: String) : PostingEvent()
}

sealed class SignOutEvent {
    data class SignOutVisibilityChanged(val isVisible: Boolean) : SignOutEvent()
    data class WithdrawVisibilityChanged(val isVisible: Boolean) : SignOutEvent()
}

sealed class UpdateFormEvent {
    data class InitialProfileUpdated(val imageUrl: String?) : UpdateFormEvent()
    data class ProfileChanged(val image: MultipartBody.Part?) : UpdateFormEvent()
    data class ProfileUriChanged(val uri: Uri?) : UpdateFormEvent()
    data class IsProfileChanged(val isChanged: Boolean) : UpdateFormEvent()
    data class IsProfileDefault(val isDefault: Boolean) : UpdateFormEvent()
    data class IsNameChanged(val isNameChanged: Boolean) : UpdateFormEvent()
    data class NicknameChanged(val nickname: String) : UpdateFormEvent()
    data class NicknamePostDataChanged(val postData: String) : UpdateFormEvent()
    data class DropdownVisibilityChanged(val isVisible: Boolean) : UpdateFormEvent()
    object Submit: UpdateFormEvent()

}
