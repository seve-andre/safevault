package com.mitch.safevault.core.util.validator.password

sealed interface PasswordValidationError
sealed interface PasswordAuthError

sealed interface PasswordError {
    data object EmptyField : PasswordValidationError
    data object InputTooShort : PasswordValidationError
    data object NoLowercaseLetter : PasswordValidationError
    data object NoUppercaseLetter : PasswordValidationError
    data object NoNumber : PasswordValidationError
    data object NoSpecialCharacter : PasswordValidationError
    data object WrongPassword : PasswordAuthError
}
