package com.mitch.safevault.core.util.validator.email

sealed interface EmailAuthError
sealed interface EmailValidationError

sealed interface EmailError {
    data object EmptyField : EmailValidationError
    data object NotAnEmail : EmailValidationError
    data object NoExistingAccount : EmailAuthError
    data object AlreadyExistingAccount : EmailAuthError
}
