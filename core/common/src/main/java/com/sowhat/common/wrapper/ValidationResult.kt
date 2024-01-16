package com.sowhat.common.wrapper

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
