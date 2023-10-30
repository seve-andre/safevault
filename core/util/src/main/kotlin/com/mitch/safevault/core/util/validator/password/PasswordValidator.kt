package com.mitch.safevault.core.util.validator.password

import com.mitch.safevault.core.util.validator.Validator

class PasswordValidator : Validator<String, PasswordValidationResult> {

    override fun validate(toValidate: String): PasswordValidationResult {
        val errors = listOf(
            toValidate.isBlank() to PasswordError.Validation.EmptyField,
            (toValidate.length < PasswordConstraints.MIN_LENGTH) to PasswordError.Validation.InputTooShort,
            toValidate.none { it.isLowerCase() } to PasswordError.Validation.NoLowercaseLetter,
            toValidate.none { it.isUpperCase() } to PasswordError.Validation.NoUppercaseLetter,
            toValidate.none { it.isDigit() } to PasswordError.Validation.NoNumber,
            toValidate.none { it in PasswordConstraints.SPECIAL_CHARACTERS } to PasswordError.Validation.NoSpecialCharacter
        )
            .filter { it.first }
            .map { it.second }

        return when {
            errors.isNotEmpty() -> PasswordValidationResult.InvalidPassword(reasons = errors)
            else -> PasswordValidationResult.Success
        }
    }
}
