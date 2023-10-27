package com.mitch.safevault.core.util.validator.password

sealed interface PasswordValidationError : PasswordError {
    data object EmptyField : PasswordValidationError
    data object InputTooShort : PasswordValidationError
    data object NoLowercaseLetter : PasswordValidationError
    data object NoUppercaseLetter : PasswordValidationError
    data object NoNumber : PasswordValidationError
    data object NoSpecialCharacter : PasswordValidationError
}
sealed interface PasswordAuthError : PasswordError {
    data object WrongPassword : PasswordAuthError
}

sealed interface PasswordError
