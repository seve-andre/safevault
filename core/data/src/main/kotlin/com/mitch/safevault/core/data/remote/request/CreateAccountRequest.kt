package com.mitch.safevault.core.data.remote.request

data class CreateAccountRequest(
    val username: String,
    val email: String,
    val password: String
)
