package com.sowhat.post_domain.use_case

import com.sowhat.common.model.ValidationResult
import com.sowhat.designsystem.common.MoodItem

class ValidateCurrentMoodUseCase {
    operator fun invoke(currentMood: MoodItem?): ValidationResult {
        if (currentMood?.postData.isNullOrBlank()) {
            return ValidationResult(isValid = false)
        }

        if (currentMood?.postData !in listOf("FEELING001", "FEELING002", "FEELING003", "FEELING004")) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}