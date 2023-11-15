package com.mitch.safevault.core.data.datasource

import com.mitch.safevault.core.data.remote.AuthApi
import com.mitch.safevault.core.data.remote.AuthApiResponse
import com.mitch.safevault.core.data.remote.request.LogInRequest
import com.mitch.safevault.core.domain.usecase.LogInResult
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
}
