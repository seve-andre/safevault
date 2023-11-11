package com.mitch.safevault.core.data.datasource

import com.mitch.safevault.core.data.remote.AuthApi
import com.mitch.safevault.core.data.remote.AuthApiResponse
import com.mitch.safevault.core.data.remote.request.LogInRequest
import com.mitch.safevault.core.datastore.UserFlowDataStore
import com.mitch.safevault.core.domain.usecase.LogInResult
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApi,
    private val userFlowDataStore: UserFlowDataStore
) {
    suspend fun logIn(
        email: String,
        password: String
    ): LogInResult {
        val response = api.logIn(LogInRequest(email, password))
        if (response is AuthApiResponse.Success) {
            userFlowDataStore.logUserIn()
        }

        return when (response) {
            is AuthApiResponse.Error -> LogInResult.Error(
                emailError = response.emailError,
                passwordError = response.passwordError
            )

            AuthApiResponse.Success -> LogInResult.Success
        }
    }
}
