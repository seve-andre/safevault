package com.mitch.safevault.core.util.validator.email

import com.mitch.safevault.core.util.validator.ValidationMatcher
import com.mitch.safevault.core.util.validator.Validator

class EmailValidator(
    private val emailMatcher: ValidationMatcher<String>
) : Validator<String, EmailValidationResult> {

    override fun validate(toValidate: String): EmailValidationResult {
        return when {
            toValidate.isBlank() -> EmailValidationResult.InvalidEmail(EmailError.EmptyField)
            emailMatcher.notMatches(toValidate) -> EmailValidationResult.InvalidEmail(EmailError.NoMatch)
            else -> EmailValidationResult.Success
        }
    }
}
