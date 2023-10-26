package com.mitch.safevault.core.data.repository

import com.mitch.safevault.core.data.datasource.AuthRemoteDataSource
import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.domain.usecase.LogInResult
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val auth: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun logIn(email: String, password: String): LogInResult {
        return auth.logIn(email, password)
    }
}
