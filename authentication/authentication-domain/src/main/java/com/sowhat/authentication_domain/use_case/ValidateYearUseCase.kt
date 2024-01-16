package com.sowhat.authentication_domain.use_case

import androidx.compose.ui.res.stringResource
import com.sowhat.authentication_domain.R
import com.sowhat.common.wrapper.Resource
import com.sowhat.common.wrapper.UiState
import com.sowhat.common.wrapper.ValidationResult

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