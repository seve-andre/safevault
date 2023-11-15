package com.mitch.safevault.feature.auth.login

import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.domain.usecase.LogInResult
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError

class FakeAuthRepository : AuthRepository {

    private val savedLogins = mapOf(
        correctEmailGenerator() to correctPasswordGenerator()
    )

    override suspend fun logIn(email: String, password: String): LogInResult {
        return when {
            savedLogins.any { it.key == email && it.value == password } -> LogInResult.Success
            savedLogins.none { it.key == email } -> LogInResult.Error(emailError = EmailError.Auth.NoExistingAccount)
            else -> LogInResult.Error(passwordError = PasswordError.Auth.WrongPassword)
        }
    }
}
