package com.mitch.safevault.core.domain.repository

import com.mitch.safevault.core.domain.usecase.LogInResult
import com.mitch.safevault.core.domain.usecase.SignUpResult

interface AuthRepository {

    suspend fun logIn(
        email: String,
        password: String
    ): LogInResult

    suspend fun signUp(
        email: String,
        password: String
    ): SignUpResult
}
