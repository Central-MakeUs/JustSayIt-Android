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
    val profileImage: MultipartBody.Part? = null,
    val nickname: String = "",
    val isNicknameValid: Boolean = false,
)

data class PostFormState(
    val currentMood: String? = null,
    val isCurrentMoodValid: Boolean = false,
    val postText: String = "",
    val isPostTextValid: Boolean = false,
    val images: ArrayList<Uri>? = null,
    val isImageListValid: Boolean = false,
    val isOpened: Boolean = false,
    val isAnonymous: Boolean = false,
    val sympathyMoodItems: List<String>? = null,
    val isSympathyMoodItemsValid: Boolean = false
)