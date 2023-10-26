package com.mitch.safevault.core.util.validator.password

sealed interface PasswordValidationResult {
    data object Success : PasswordValidationResult
    data class InvalidPassword(val reasons: List<PasswordValidationError>) : PasswordValidationResult
}
