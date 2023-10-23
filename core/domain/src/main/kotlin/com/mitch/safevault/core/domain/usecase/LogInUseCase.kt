package com.mitch.safevault.core.domain.usecase

import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.email.EmailValidationResult
import com.mitch.safevault.core.util.validator.email.EmailValidator
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.core.util.validator.password.PasswordValidationResult
import com.mitch.safevault.core.util.validator.password.PasswordValidator
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val repository: AuthRepository
) {

    suspend fun logIn(email: String, password: String): LogInResult {
        val emailError = this.validateEmail(email)
        val passwordError = this.validatePassword(password)

        if (hasValidationErrors(emailError, passwordError)) {
            return LogInResult.Error(emailError, passwordError)
        }

        return repository.logIn(email, password)
    }

    fun validateEmail(email: String): EmailError? {
        return when (val result = emailValidator.validate(email.trim())) {
            is EmailValidationResult.InvalidEmail -> result.reason
            EmailValidationResult.Success -> null
        }
    }

    fun validatePassword(password: String): PasswordError.EmptyField? {
        val passwordValidationResult = passwordValidator.validate(password.trim())
        return if (
            passwordValidationResult is PasswordValidationResult.InvalidPassword
            && PasswordError.EmptyField in passwordValidationResult.reasons
        ) {
            PasswordError.EmptyField
        } else {
            null
        }
    }

    private fun hasValidationErrors(
        emailError: EmailError?,
        passwordError: PasswordError?
    ): Boolean {
        return emailError != null || passwordError != null
    }
}

sealed interface LogInResult {
    data class Error(
        val emailError: EmailError? = null,
        val passwordError: PasswordError? = null
    ) : LogInResult

    data object Success : LogInResult
}
