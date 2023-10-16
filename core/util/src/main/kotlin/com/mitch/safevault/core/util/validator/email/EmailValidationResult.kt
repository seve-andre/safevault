package com.mitch.safevault.core.util.validator.email

sealed interface EmailValidationResult {
    data object Success : EmailValidationResult
    data class InvalidEmail(val reason: EmailError) : EmailValidationResult
}
