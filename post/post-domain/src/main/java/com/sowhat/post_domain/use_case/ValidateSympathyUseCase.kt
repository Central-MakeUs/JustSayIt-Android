package com.sowhat.post_domain.use_case

import com.sowhat.common.model.ValidationResult
import com.sowhat.designsystem.common.MoodItem

class ValidateSympathyUseCase {
    operator fun invoke(isOpen: Boolean, selectedMoodItems: List<MoodItem>): ValidationResult {
        if (!isOpen) return ValidationResult(isValid = true)

        if (isOpen && selectedMoodItems.isEmpty()) {
            return ValidationResult(isValid = false)
        }

        if (selectedMoodItems.size !in 1..4) {
            return ValidationResult(isValid = false)
        }

        selectedMoodItems.forEach {
            if (it.postData !in listOf("EMOTION001", "EMOTION002", "EMOTION003", "EMOTION004")) {
                return ValidationResult(isValid = false)
            }
        }

        return ValidationResult(isValid = true)
    }
}