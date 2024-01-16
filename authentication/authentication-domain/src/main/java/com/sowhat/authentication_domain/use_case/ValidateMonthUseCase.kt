package com.sowhat.authentication_domain.use_case

import androidx.compose.ui.res.stringResource
import com.sowhat.authentication_domain.R
import com.sowhat.common.wrapper.Resource
import com.sowhat.common.wrapper.UiState
import com.sowhat.common.wrapper.ValidationResult

class ValidateMonthUseCase {
    operator fun invoke(month: String): ValidationResult {
        val monthLengthCondition = """^\d{2}$""".toRegex()
        val monthRangeCondition = "[0-1][0-9]".toRegex()

        if (month.isBlank()) {
            return ValidationResult(isValid = false)
        }

        if (!month.matches(monthLengthCondition)) {
            return ValidationResult(isValid = false)
        }

        if (!month.matches(monthRangeCondition)) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}