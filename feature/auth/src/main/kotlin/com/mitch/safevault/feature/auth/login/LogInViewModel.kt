package com.mitch.safevault.feature.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.safevault.core.domain.usecase.LogInResult
import com.mitch.safevault.core.domain.usecase.LogInUseCase
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.password.PasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _logInState = MutableSharedFlow<LogInUiState>()
    val logInState = _logInState.asSharedFlow()

    fun logIn(
        email: String,
        password: String
    ) {
        if (!emailState.shouldStartValidation) {
            emailState.shouldStartValidation = true
        }

        if (!passwordState.shouldStartValidation) {
            passwordState.shouldStartValidation = true
        }

        viewModelScope.launch {
            _logInState.emit(LogInUiState.Loading)
            when (logInUseCase.logIn(email, password)) {
                is LogInResult.Error -> _logInState.emit(LogInUiState.Error)
                LogInResult.Success -> _logInState.emit(LogInUiState.Success)
            }
        }
    }
}
