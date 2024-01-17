package com.sowhat.post_domain.use_case

import com.sowhat.common.model.ValidationResult

class ValidateCurrentMoodUseCase {
    operator fun invoke(currentMood: String?): ValidationResult {
        if (currentMood.isNullOrBlank()) {
            return ValidationResult(isValid = false)
        }

        if (currentMood !in listOf("행복", "슬픔", "놀람", "화남")) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}