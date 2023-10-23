package com.mitch.safevault.core.data.datasource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
//    private val api: AuthApi,
    @ApplicationContext private val context: Context
) {
    suspend fun logIn(
        email: String,
        password: String
    ): Result<Unit> {
        /*val result = api.logIn(LogInRequest(email, password))
        if (result.isSuccess) {
            // datastore
        }

        return result*/

        return Result.success(Unit)
    }
}
