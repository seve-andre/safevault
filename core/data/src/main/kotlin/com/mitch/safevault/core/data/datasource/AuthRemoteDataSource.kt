package com.mitch.safevault.core.data.datasource

import com.mitch.safevault.core.data.remote.AuthApi
import com.mitch.safevault.core.data.remote.AuthApiResponse
import com.mitch.safevault.core.data.remote.request.CreateAccountRequest
import com.mitch.safevault.core.data.remote.request.LogInRequest
import com.mitch.safevault.core.domain.usecase.LogInResult
import com.mitch.safevault.core.domain.usecase.SignUpResult
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApi
) {
    suspend fun logIn(
        email: String,
        password: String
    ): LogInResult {
        return when (val response = api.logIn(LogInRequest(email, password))) {
            is AuthApiResponse.Error -> LogInResult.Error(
                emailError = response.emailError,
                passwordError = response.passwordError
            )

            AuthApiResponse.Success -> LogInResult.Success
        }
    }

    suspend fun signUp(
        email: String,
        password: String
    ): SignUpResult {
        return when (val response = api.signUp(CreateAccountRequest(email, password))) {
            is AuthApiResponse.Error -> SignUpResult.Error(
                emailError = response.emailError,
                passwordError = response.passwordError
            )

            AuthApiResponse.Success -> SignUpResult.Success
        }
    }
}
