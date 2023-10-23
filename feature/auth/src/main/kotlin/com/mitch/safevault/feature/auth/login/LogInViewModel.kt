package com.mitch.safevault.feature.auth.login

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.safevault.core.domain.usecase.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    val emailError by derivedStateOf { logInUseCase.validateEmail(email) }

    val passwordError by derivedStateOf { logInUseCase.validatePassword(password) }

    fun updateEmail(email: String) {
        this.email = email
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun logIn(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val result = logInUseCase.logIn(email, password)
        }
    }
}
