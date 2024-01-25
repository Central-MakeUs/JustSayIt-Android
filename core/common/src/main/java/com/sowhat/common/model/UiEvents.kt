package com.sowhat.common.model

import android.net.Uri
import okhttp3.MultipartBody

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

sealed class UpdateFormEvent {
    data class ProfileChanged(val image: MultipartBody.Part?) : UpdateFormEvent()
    data class NicknameChanged(val nickname: String) : UpdateFormEvent()
    object Submit: UpdateFormEvent()

}

sealed class PostFormEvent {
    data class CurrentMoodChanged(val mood: String) : PostFormEvent()
    data class ImageListUpdated(val images: List<Uri>?) : PostFormEvent()
    data class PostTextChanged(val text: String) : PostFormEvent()
    data class OpenChanged(val open: Boolean) : PostFormEvent()
    data class AnonymousChanged(val anonymous: Boolean) : PostFormEvent()
    data class SympathyItemsChanged(val sympathyItems: List<String>) : PostFormEvent()
    data class DialogVisibilityChanged(val isVisible: Boolean) : PostFormEvent()
}

sealed class PostingEvent {
    object NavigateUp : PostingEvent()
    data class Error(val message: String) : PostingEvent()
}

sealed class SignOutEvent {
    data class SignOutVisibilityChanged(val isVisible: Boolean) : SignOutEvent()
    data class WithdrawVisibilityChanged(val isVisible: Boolean) : SignOutEvent()
}

sealed class SignOutPostingEvent {
    object Submit : SignOutPostingEvent()
    object SignOutNavigateUp : SignOutPostingEvent()
    object WithdrawNavigateUp : SignOutPostingEvent()
}
