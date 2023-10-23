package com.mitch.safevault.feature.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.safevault.core.domain.usecase.LogInUseCase
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.password.PasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    val emailState by mutableStateOf(
        EmailState(onValidateEmail = logInUseCase::validateEmail)
    )

    val passwordState by mutableStateOf(
        PasswordState(onValidatePassword = logInUseCase::validatePassword)
    )

    fun logIn(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val result = logInUseCase.logIn(email, password)
        }
    }
}
