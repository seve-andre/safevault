package com.mitch.safevault.core.data.remote

import com.mitch.safevault.core.data.remote.request.CreateAccountRequest
import com.mitch.safevault.core.data.remote.request.LogInRequest
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
import javax.inject.Inject

class DefaultAuthApi @Inject constructor() : AuthApi {
    override suspend fun signUp(request: CreateAccountRequest): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun logIn(request: LogInRequest): AuthApiResponse {
        return AuthApiResponse.Error(emailError = EmailError.Auth.NoExistingAccount)
    }
}

sealed interface AuthApiResponse {
    data object Success : AuthApiResponse
    data class Error(
        val emailError: EmailError.Auth? = null,
        val passwordError: PasswordError.Auth? = null
    ) : AuthApiResponse
}
