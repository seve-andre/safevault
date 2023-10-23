package com.mitch.safevault.domain.repository

import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.domain.usecase.LogInResult

class FakeAuthRepository : AuthRepository {
    override suspend fun logIn(email: String, password: String): LogInResult {
        return LogInResult.Success
    }
}
