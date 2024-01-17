package com.sowhat.common.model

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
