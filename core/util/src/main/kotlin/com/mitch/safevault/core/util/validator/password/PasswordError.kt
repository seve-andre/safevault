package com.mitch.safevault.core.util.validator.password

sealed interface PasswordError {
    data object EmptyField : PasswordError
    data object InputTooShort : PasswordError
    data object NoLowercaseLetter : PasswordError
    data object NoUppercaseLetter : PasswordError
    data object NoNumber : PasswordError
    data object NoSpecialCharacter : PasswordError
}
