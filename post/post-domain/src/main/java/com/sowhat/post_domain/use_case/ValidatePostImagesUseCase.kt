package com.sowhat.post_domain.use_case

import android.net.Uri
import com.sowhat.common.model.ValidationResult

class ValidatePostImagesUseCase {
    operator fun invoke(images: List<Uri>?): ValidationResult {
        if (images?.size!! > 4) {
            return ValidationResult(isValid = false)
        }

        return ValidationResult(isValid = true)
    }
}