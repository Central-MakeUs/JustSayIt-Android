package com.sowhat.post_domain.use_case

import com.sowhat.common.model.ValidationResult

class ValidateSympathyUseCase {
    operator fun invoke(selectedMoodItems: List<String>): ValidationResult {
        if (selectedMoodItems.isEmpty()) {
            return ValidationResult(isValid = false)
        }

        if (selectedMoodItems.size !in 1..4) {
            return ValidationResult(isValid = false)
        }

        selectedMoodItems.forEach {
            if (it !in listOf("행복", "슬픔", "놀람", "화남")) {
                return ValidationResult(isValid = false)
            }
        }

        return ValidationResult(isValid = true)
    }
}