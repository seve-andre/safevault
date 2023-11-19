package com.mitch.safevault.core.data.remote

import com.mitch.safevault.core.data.remote.request.CreateAccountRequest
import com.mitch.safevault.core.data.remote.request.LogInRequest

interface AuthApi {

    suspend fun logIn(
        request: LogInRequest
    ): AuthApiResponse

    suspend fun signUp(
        request: CreateAccountRequest
    ): AuthApiResponse
}
