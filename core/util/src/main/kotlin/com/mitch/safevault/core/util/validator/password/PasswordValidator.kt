package com.mitch.safevault.core.util.validator.password

import com.mitch.safevault.core.util.validator.Validator

class PasswordValidator : Validator<String, PasswordValidationResult> {

    override fun validate(toValidate: String): PasswordValidationResult {
        val errors = listOf(
            toValidate.isBlank() to PasswordError.EmptyField,
            (toValidate.length < PasswordConstraints.MIN_LENGTH) to PasswordError.InputTooShort,
            toValidate.none { it.isLowerCase() } to PasswordError.NoLowercaseLetter,
            toValidate.none { it.isUpperCase() } to PasswordError.NoUppercaseLetter,
            toValidate.none { it.isDigit() } to PasswordError.NoNumber,
            toValidate.none { it in PasswordConstraints.SPECIAL_CHARACTERS } to PasswordError.NoSpecialCharacter
        )
            .filter { it.first }
            .map { it.second }

        return when {
            errors.isNotEmpty() -> PasswordValidationResult.InvalidPassword(errors)
            else -> PasswordValidationResult.Success
        }
    }
}
