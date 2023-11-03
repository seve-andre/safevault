package com.mitch.safevault.feature.auth.login

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.safevault.core.domain.usecase.LogInResult
import com.mitch.safevault.core.domain.usecase.LogInUseCase
import com.mitch.safevault.core.ui.component.PasswordTextFieldState
import com.mitch.safevault.core.ui.component.TextFieldState
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EmailState(
    val textFieldState: TextFieldState,
    val validationError: EmailError.Validation?
)

data class PasswordState(
    val textFieldState: PasswordTextFieldState,
    val validationError: PasswordError.Validation.EmptyField?
)

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    private var emailTextFieldState by mutableStateOf(TextFieldState())
    private var hasEmailValidationStarted by mutableStateOf(false)
    private val emailValidationError by derivedStateOf {
        if (hasEmailValidationStarted) {
            logInUseCase.validateEmail(emailTextFieldState.text)
        } else {
            null
        }
    }

    fun startEmailValidation() {
        hasEmailValidationStarted = true
    }

    val emailState = snapshotFlow { EmailState(emailTextFieldState, emailValidationError) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EmailState(
                textFieldState = emailTextFieldState,
                validationError = emailValidationError
            )
        )

    private var passwordTextFieldState by mutableStateOf(PasswordTextFieldState())
    private var hasPasswordValidationStarted by mutableStateOf(false)
    private val passwordValidationError by derivedStateOf {
        if (hasPasswordValidationStarted) {
            logInUseCase.validatePassword(passwordTextFieldState.text)
        } else {
            null
        }
    }

    fun startPasswordValidation() {
        hasPasswordValidationStarted = true
    }

    val passwordState =
        snapshotFlow { PasswordState(passwordTextFieldState, passwordValidationError) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PasswordState(
                    textFieldState = passwordTextFieldState,
                    validationError = passwordValidationError
                )
            )

    private val _logInUiState = MutableStateFlow<LogInUiState>(LogInUiState.Idle)
    val logInUiState = _logInUiState.asStateFlow()

    fun logIn() {
        if (!hasEmailValidationStarted) {
            hasEmailValidationStarted = true
        }

        if (!hasPasswordValidationStarted) {
            this.startPasswordValidation()
        }

        if (emailValidationError != null || passwordValidationError != null) {
            return
        }

        viewModelScope.launch {
            _logInUiState.value = LogInUiState.Loading
            val logInResult = logInUseCase.logIn(emailTextFieldState.text, passwordTextFieldState.text)
            when (logInResult) {
                is LogInResult.Error -> _logInUiState.value = LogInUiState.AuthenticationFailed(
                    emailAuthError = logInResult.emailError,
                    passwordAuthError = logInResult.passwordError
                )

                LogInResult.Success -> _logInUiState.value = LogInUiState.Success
            }
        }
    }
}
