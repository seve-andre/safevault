package com.mitch.safevault.core.data.datasource

import android.content.Context
import com.mitch.safevault.core.data.remote.AuthApi
import com.mitch.safevault.core.data.remote.AuthApiResponse
import com.mitch.safevault.core.data.remote.request.LogInRequest
import com.mitch.safevault.core.domain.usecase.LogInResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApi,
    @ApplicationContext private val context: Context
) {
    suspend fun logIn(
        email: String,
        password: String
    ): LogInResult {
        val response = api.logIn(LogInRequest(email, password))
        if (response is AuthApiResponse.Success) {
            // save to datastore that user is now logged
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
