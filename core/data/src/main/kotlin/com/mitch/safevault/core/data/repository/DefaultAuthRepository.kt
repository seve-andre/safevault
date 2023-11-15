package com.mitch.safevault.core.data.repository

import com.mitch.safevault.core.data.datasource.AuthRemoteDataSource
import com.mitch.safevault.core.datastore.UserFlowLocalDataSource
import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.domain.usecase.LogInResult
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userFlowLocalDataSource: UserFlowLocalDataSource
) : AuthRepository {

    override suspend fun logIn(email: String, password: String): LogInResult {
        val result = authRemoteDataSource.logIn(email, password)
        if (result is LogInResult.Success) {
            userFlowLocalDataSource.logUserIn()
        }

        return result
    }
}
