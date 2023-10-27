package com.mitch.safevault.core.util.validator.email

sealed interface EmailAuthError : EmailError {
    data object NoExistingAccount : EmailAuthError
    data object AlreadyExistingAccount : EmailAuthError
}
sealed interface EmailValidationError : EmailError {
    data object EmptyField : EmailValidationError
    data object NotAnEmail : EmailValidationError
}

sealed interface EmailError
