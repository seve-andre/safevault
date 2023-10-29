package com.mitch.safevault.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.safevault.core.designsystem.theme.SafeVaultMaterialTheme
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.password.PasswordState
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.feature.auth.R
import com.mitch.safevault.feature.auth.login.component.LogInForm
import com.mitch.safevault.feature.auth.login.component.NoExistingAccountErrorCard

@Composable
internal fun LogInRoute(
    viewModel: LogInViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit
) {
    val logInUiState by viewModel.logInUiState.collectAsStateWithLifecycle()

    LogInScreen(
        logInUiState = logInUiState,
        emailState = viewModel.emailState,
        passwordState = viewModel.passwordState,
        onLogInSubmitted = viewModel::logIn,
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@Composable
internal fun LogInScreen(
    logInUiState: LogInUiState,
    emailState: EmailState,
    passwordState: PasswordState,
    onLogInSubmitted: (String, String) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login")
        if (logInUiState is LogInUiState.AuthenticationFailed) {
            if (logInUiState.emailAuthError is EmailError.Auth.NoExistingAccount) {
                NoExistingAccountErrorCard()
            }

            if (logInUiState.passwordAuthError != null) {
                Text(text = stringResource(id = R.string.password_error_wrong))
            }
        }
        LogInForm(
            emailState = emailState,
            passwordState = passwordState,
            onSubmit = {
                keyboardController?.hide()
                onLogInSubmitted(emailState.email, passwordState.password)
            }
        )

        TextButton(onClick = onNavigateToSignUp) {
            Text(text = stringResource(id = R.string.sign_up_now))
        }
    }
}


@Preview
@Composable
private fun LogInScreenIdlePreview() {
    SafeVaultMaterialTheme {
        LogInScreen(
            logInUiState = LogInUiState.Idle,
            emailState = EmailState(onValidateEmail = { _ -> null }),
            passwordState = PasswordState(onValidatePassword = { _ -> null }),
            onLogInSubmitted = { _, _ -> },
            onNavigateToSignUp = { }
        )
    }
}

@Preview
@Composable
private fun LogInScreenValidationErrorsPreview() {
    val emailState = EmailState(
        onValidateEmail = { _ -> EmailError.Validation.EmptyField },
        shouldValidateImmediately = true
    )

    val passwordState = PasswordState(
        onValidatePassword = { _ -> PasswordError.Validation.EmptyField },
        shouldValidateImmediately = true
    )

    SafeVaultMaterialTheme {
        LogInScreen(
            logInUiState = LogInUiState.Idle,
            emailState = emailState,
            passwordState = passwordState,
            onLogInSubmitted = { _, _ -> },
            onNavigateToSignUp = { }
        )
    }
}

@Preview
@Composable
private fun LogInScreenPasswordVisibilityPreview() {
    val passwordState = PasswordState(onValidatePassword = { _ -> null })
    passwordState.password = "Preview"
    passwordState.togglePasswordVisibility()

    SafeVaultMaterialTheme {
        LogInScreen(
            logInUiState = LogInUiState.Idle,
            emailState = EmailState(onValidateEmail = { _ -> null }),
            passwordState = passwordState,
            onLogInSubmitted = { _, _ -> },
            onNavigateToSignUp = { }
        )
    }
}

@Preview
@Composable
private fun LogInScreenAuthErrorPreview() {
    SafeVaultMaterialTheme {
        LogInScreen(
            logInUiState = LogInUiState.AuthenticationFailed(
                emailAuthError = EmailError.Auth.NoExistingAccount,
                passwordAuthError = PasswordError.Auth.WrongPassword
            ),
            emailState = EmailState(onValidateEmail = { _ -> null }),
            passwordState = PasswordState(onValidatePassword = { _ -> null }),
            onLogInSubmitted = { _, _ -> },
            onNavigateToSignUp = { }
        )
    }
}
