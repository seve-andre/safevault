package com.mitch.safevault.core.util.validator.password

sealed interface PasswordError {
    sealed interface Validation : PasswordError {
        data object EmptyField : Validation
        data object InputTooShort : Validation
        data object NoLowercaseLetter : Validation
        data object NoUppercaseLetter : Validation
        data object NoNumber : Validation
        data object NoSpecialCharacter : Validation
    }
    sealed interface Auth : PasswordError {
        data object WrongPassword : Auth
    }
}
