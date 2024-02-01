package com.sowhat.common.model

import android.net.Uri
import okhttp3.MultipartBody

data class RegistrationFormState(
    val profileImage: MultipartBody.Part? = null,
    val nickname: String = "",
    val isNicknameValid: Boolean = false,
    val gender: String = "",
    val isGenderValid: Boolean = false,
    val year: String = "",
    val isYearValid: Boolean = false,
    val month: String = "",
    val isMonthValid: Boolean = false,
    val day: String = "",
    val isDayValid: Boolean = false
)

data class UpdateFormState(
    val isProfileChanged: Boolean = false,
    val isNameChanged: Boolean = false,
    val isDefault: Boolean = false,
    val initialImageUrl: String = "",
    val newImageUri: Uri? = null,
    val isPopupMenuVisible: Boolean = false,
    val profileImage: MultipartBody.Part? = null,
    val nickname: String = "",
    val nicknamePostData: String = "",
    val isNicknameValid: Boolean = false,
)
