package com.sowhat.authentication_domain.use_case

import com.sowhat.common.model.ValidationResult

class ValidateNicknameUseCase {
    operator fun invoke(nickname: String): ValidationResult {
        if (nickname.isBlank()) {
            return ValidationResult(isValid = false)
        }

        if (nickname.length !in 2..12) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}

// middle layer between viewmodel and data layer
// access to the database/repository/datasource...
// reason why we use it? excellent way to follow the SRP(각 클래스가 하나의 일만 하도록 함)
// 닉네임의 유효 조건이 바뀔 경우, 이 클래스만 바꾸면 되므로 SRP 달성 가능
