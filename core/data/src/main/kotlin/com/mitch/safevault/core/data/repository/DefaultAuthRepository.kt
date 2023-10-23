package com.mitch.safevault.core.data.repository

import com.mitch.safevault.core.data.datasource.AuthRemoteDataSource
import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.domain.usecase.LogInResult
import com.mitch.safevault.core.util.validator.password.PasswordError
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val auth: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun logIn(email: String, password: String): LogInResult {
        val result = auth.logIn(email, password)
        return if (result.isSuccess) {
            LogInResult.Success
        } else {
            LogInResult.Error(passwordError = PasswordError.NoMatch)
        }
    }
}
