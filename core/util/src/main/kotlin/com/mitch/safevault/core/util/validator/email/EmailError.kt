package com.mitch.safevault.core.util.validator.email

sealed interface EmailError {
    sealed interface Auth : EmailError {
        data object NoExistingAccount : Auth
        data object AlreadyExistingAccount : Auth
    }
    sealed interface Validation : EmailError {
        data object EmptyField : Validation
        data object NotAnEmail : Validation
    }
}
