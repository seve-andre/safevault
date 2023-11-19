package com.mitch.safevault.core.domain.usecase

import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.email.EmailValidationResult
import com.mitch.safevault.core.util.validator.email.EmailValidator
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.core.util.validator.password.PasswordValidationResult
import com.mitch.safevault.core.util.validator.password.PasswordValidator
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val repository: AuthRepository
) {

    suspend fun signUp(email: String, password: String): SignUpResult {
        return repository.signUp(email, password)
    }

    fun validateEmail(email: String): EmailError.Validation? {
        return when (val result = emailValidator.validate(email.trim())) {
            is EmailValidationResult.InvalidEmail -> result.reason
            EmailValidationResult.Success -> null
        }
    }

    fun validatePassword(password: String): List<PasswordError.Validation>? {
        return when (val result = passwordValidator.validate(password.trim())) {
            is PasswordValidationResult.InvalidPassword -> result.reasons
            PasswordValidationResult.Success -> null
        }
    }
}

sealed interface SignUpResult {
    data class Error(
        val emailError: EmailError.Auth? = null,
        val passwordError: PasswordError.Auth? = null
    ) : SignUpResult

    data object Success : SignUpResult
}
