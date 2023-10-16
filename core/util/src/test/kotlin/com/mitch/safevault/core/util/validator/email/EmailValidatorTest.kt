package com.mitch.safevault.core.util.validator.email

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.mitch.safevault.core.util.validator.ValidationMatcher
import org.junit.Test

internal class EmailValidatorTest {

    private val emailMatcher = object : ValidationMatcher<String> {
        override fun matches(toMatch: String): Boolean {
            return Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
                .matches(toMatch)
        }
    }

    private val validator = EmailValidator(emailMatcher)

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

        assertThat(result.reason).isEqualTo(EmailError.NoMatch)
    }

    @Test
    fun `when correct email, validator returns success`() {
        val result = validator.validate("andrea.severi.dev@gmail.com")

        assertThat(result).isInstanceOf(EmailValidationResult.Success::class)
    }
}
