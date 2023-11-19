package com.mitch.safevault.feature.auth.signup

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.safevault.core.domain.usecase.SignUpResult
import com.mitch.safevault.core.domain.usecase.SignUpUseCase
import com.mitch.safevault.core.ui.component.PasswordTextFieldState
import com.mitch.safevault.core.ui.component.TextFieldState
import com.mitch.safevault.feature.auth.EmailState
import com.mitch.safevault.feature.auth.PasswordSignUpState
import com.mitch.safevault.feature.auth.login.LogInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val emailTextFieldState = TextFieldState()
    private var hasEmailValidationStarted by mutableStateOf(false)
    private val emailValidationError by derivedStateOf {
        if (hasEmailValidationStarted) {
            signUpUseCase.validateEmail(emailTextFieldState.text)
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

    private val passwordTextFieldState = PasswordTextFieldState()
    private var hasPasswordValidationStarted by mutableStateOf(false)
    private val passwordValidationErrors by derivedStateOf {
        if (hasPasswordValidationStarted) {
            signUpUseCase.validatePassword(passwordTextFieldState.text)
        } else {
            null
        }
    }

    fun startPasswordValidation() {
        hasPasswordValidationStarted = true
    }

    val passwordState =
        snapshotFlow { PasswordSignUpState(passwordTextFieldState, passwordValidationErrors) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PasswordSignUpState(
                    textFieldState = passwordTextFieldState,
                    validationErrors = passwordValidationErrors
                )
            )

    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val signUpUiState = _signUpUiState.asStateFlow()

    fun signUp() {
        if (!hasEmailValidationStarted) {
            this.startEmailValidation()
        }

        if (!hasPasswordValidationStarted) {
            this.startPasswordValidation()
        }

        if (emailValidationError != null || passwordValidationErrors != null) {
            return
        }

        viewModelScope.launch {
            _signUpUiState.value = SignUpUiState.Loading

            val signUpResult = signUpUseCase.signUp(
                email = emailTextFieldState.text,
                password = passwordTextFieldState.text
            )
            when (signUpResult) {
                is SignUpResult.Error -> _signUpUiState.value = SignUpUiState.AuthenticationFailed(
                    emailAuthError = signUpResult.emailError,
                    passwordAuthError = signUpResult.passwordError
                )

                SignUpResult.Success -> _signUpUiState.value = SignUpUiState.Success
            }
        }
    }
}
