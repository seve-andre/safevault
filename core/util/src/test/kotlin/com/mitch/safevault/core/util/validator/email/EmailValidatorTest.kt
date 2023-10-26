package com.mitch.safevault.core.util.validator.email

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.Test

internal class EmailValidatorTest {

    private val validator = Rfc5322EmailValidator()

    @Test
    fun `when blank email, validator returns empty field error`() {
        val emptyEmail = validator.validate("") as EmailValidationResult.InvalidEmail
        val whitespacesOnlyEmail = validator.validate("  ") as EmailValidationResult.InvalidEmail

        assertThat(emptyEmail.reason).isEqualTo(EmailError.EmptyField)
        assertThat(whitespacesOnlyEmail.reason).isEqualTo(EmailError.EmptyField)
    }

    @Test
    fun `when email does not match regex, validator returns no match error`() {
        val result = validator.validate("andrea.com") as EmailValidationResult.InvalidEmail

        assertThat(result.reason).isEqualTo(EmailError.NotAnEmail)
    }

    @Test
    fun `when correct email, validator returns success`() {
        val result = validator.validate("andrea.severi.dev@gmail.com")

        assertThat(result).isInstanceOf(EmailValidationResult.Success::class)
    }
}
