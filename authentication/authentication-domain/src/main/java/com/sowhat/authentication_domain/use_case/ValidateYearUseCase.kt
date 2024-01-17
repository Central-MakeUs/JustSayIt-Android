package com.sowhat.authentication_domain.use_case

import com.sowhat.common.model.ValidationResult

class ValidateYearUseCase {
    operator fun invoke(year: String): ValidationResult {
        val yearCondition = """^\d{4}$""".toRegex()

        if (year.isBlank()) {
            return ValidationResult(isValid = false)
        }

        if (!year.matches(yearCondition)) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}