package com.sowhat.presentation.common

import android.graphics.Bitmap
import okhttp3.MultipartBody
import java.io.File

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