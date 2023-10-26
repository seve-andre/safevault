package com.mitch.safevault.core.data.remote

import com.mitch.safevault.core.data.remote.request.CreateAccountRequest
import com.mitch.safevault.core.data.remote.request.LogInRequest
import com.mitch.safevault.core.util.validator.email.EmailAuthError
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordAuthError
import com.mitch.safevault.core.util.validator.password.PasswordError

class DefaultAuthApi : AuthApi {
    override suspend fun signUp(request: CreateAccountRequest): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun logIn(request: LogInRequest): AuthApiResponse {
        return AuthApiResponse.Error(emailError = EmailError.NoExistingAccount)
    }
}

sealed interface AuthApiResponse {
    data object Success : AuthApiResponse
    data class Error(
        val emailError: EmailAuthError? = null,
        val passwordError: PasswordAuthError? = null
    ) : AuthApiResponse
}
