package com.mitch.safevault.core.util.validator.password

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isInstanceOf
import org.junit.Test

internal class PasswordValidatorTest {

    private val validator = PasswordValidator()

    @Test
    fun `when blank password, validator returns empty field error`() {
        val emptyPassword = validator.validate("") as PasswordValidationResult.InvalidPassword
        val whitespacesOnlyPassword = validator.validate("   ") as PasswordValidationResult.InvalidPassword

        assertThat(emptyPassword.reasons).contains(PasswordError.Validation.EmptyField)
        assertThat(whitespacesOnlyPassword.reasons).contains(PasswordError.Validation.EmptyField)
    }

    @Test
    fun `when short password, validator returns too short error`() {
        val result = validator.validate("1234567") as PasswordValidationResult.InvalidPassword

        assertThat(result.reasons).contains(PasswordError.Validation.InputTooShort)
    }

    @Test
    fun `when only uppercase letters in password, validator returns no lowercase letters error`() {
        val result = validator.validate("PASSWORD") as PasswordValidationResult.InvalidPassword

        assertThat(result.reasons).contains(PasswordError.Validation.NoLowercaseLetter)
    }

    @Test
    fun `when only lowercase letters in password, validator returns no uppercase letters error`() {
        val result = validator.validate("password") as PasswordValidationResult.InvalidPassword

        assertThat(result.reasons).contains(PasswordError.Validation.NoUppercaseLetter)
    }

    @Test
    fun `when only letters in password, validator returns no number error`() {
        val result = validator.validate("password") as PasswordValidationResult.InvalidPassword

        assertThat(result.reasons).contains(PasswordError.Validation.NoNumber)
    }

    @Test
    fun `when no special characters in password, validator returns no special characters error`() {
        val result = validator.validate("password") as PasswordValidationResult.InvalidPassword

        assertThat(result.reasons).contains(PasswordError.Validation.NoSpecialCharacter)
    }

    @Test
    fun `when correct password, validator returns success`() {
        val result = validator.validate("Abcdef1#")

        assertThat(result).isInstanceOf(PasswordValidationResult.Success::class)
    }
}
