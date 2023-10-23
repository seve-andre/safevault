package com.mitch.safevault.core.data.remote

import com.mitch.safevault.core.data.remote.request.CreateAccountRequest
import com.mitch.safevault.core.data.remote.request.LogInRequest

class DefaultAuthApi : AuthApi {
    override suspend fun signUp(request: CreateAccountRequest): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun logIn(request: LogInRequest): Result<Unit> {
        TODO("Not yet implemented")
    }
}
