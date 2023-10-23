package com.mitch.safevault.core.domain.repository

import com.mitch.safevault.core.domain.usecase.LogInResult

interface AuthRepository {

    suspend fun logIn(
        email: String,
        password: String
    ): LogInResult
}
