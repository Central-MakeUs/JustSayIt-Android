package com.sowhat.authentication_domain.use_case

import com.sowhat.common.model.ValidationResult

class ValidateDayUseCase {
    operator fun invoke(day: String): ValidationResult {
        val dayLengthCondition = """^\d{2}$""".toRegex()
        val dayRangeCondition = "[0-3][0-9]".toRegex()

        if (day.isBlank()) {
            return ValidationResult(isValid = false)
        }

        if (!day.matches(dayLengthCondition)) {
            return ValidationResult(isValid = false)
        }

        if (!day.matches(dayRangeCondition)) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}