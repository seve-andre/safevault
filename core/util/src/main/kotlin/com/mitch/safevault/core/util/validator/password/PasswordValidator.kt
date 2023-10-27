package com.mitch.safevault.core.util.validator.password

import com.mitch.safevault.core.util.validator.Validator

class PasswordValidator : Validator<String, PasswordValidationResult> {

    override fun validate(toValidate: String): PasswordValidationResult {
        val errors = listOf(
            toValidate.isBlank() to PasswordValidationError.EmptyField,
            (toValidate.length < PasswordConstraints.MIN_LENGTH) to PasswordValidationError.InputTooShort,
            toValidate.none { it.isLowerCase() } to PasswordValidationError.NoLowercaseLetter,
            toValidate.none { it.isUpperCase() } to PasswordValidationError.NoUppercaseLetter,
            toValidate.none { it.isDigit() } to PasswordValidationError.NoNumber,
            toValidate.none { it in PasswordConstraints.SPECIAL_CHARACTERS } to PasswordValidationError.NoSpecialCharacter
        )
            .filter { it.first }
            .map { it.second }

        return when {
            errors.isNotEmpty() -> PasswordValidationResult.InvalidPassword(errors)
            else -> PasswordValidationResult.Success
        }
    }
}
