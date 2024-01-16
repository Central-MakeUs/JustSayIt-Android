package com.sowhat.authentication_domain.use_case

import androidx.compose.ui.res.stringResource
import com.sowhat.authentication_domain.R
import com.sowhat.common.wrapper.Resource
import com.sowhat.common.wrapper.UiState
import com.sowhat.common.wrapper.ValidationResult

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