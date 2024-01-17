package com.sowhat.post_domain.use_case

import com.sowhat.common.model.ValidationResult

class ValidatePostTextUseCase {
    operator fun invoke(text: String?): ValidationResult {

        if (text.isNullOrBlank()) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}