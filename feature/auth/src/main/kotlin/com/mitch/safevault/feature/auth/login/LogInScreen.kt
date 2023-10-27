package com.mitch.safevault.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.core.designsystem.theme.SafeVaultMaterialTheme
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.email.EmailTextField
import com.mitch.safevault.core.ui.component.password.PasswordState
import com.mitch.safevault.core.ui.component.password.PasswordTextField
import com.mitch.safevault.core.ui.extensions.m3.contentPadding
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.feature.auth.R
import kotlinx.coroutines.delay
import com.mitch.safevault.core.util.R as utilR

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
    val emailFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(1_000)
        emailFocusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (logInUiState is LogInUiState.AuthenticationFailed) {
            if (logInUiState.emailAuthError != null) {
                Card(
                    modifier = Modifier.size(width = 250.dp, height = 100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(CardDefaults.contentPadding),
                        horizontalArrangement = Arrangement.spacedBy(padding.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = SafeVaultIcons.Error, contentDescription = null)
                        Text(text = stringResource(id = R.string.no_existing_account))
                    }
                }
            }

            if (logInUiState.passwordAuthError != null) {
                Text(text = stringResource(id = R.string.password_error_wrong))
            }
        }
        EmailTextField(
            emailState = emailState,
            modifier = Modifier.focusRequester(emailFocusRequester)
        )
        PasswordTextField(
            passwordState = passwordState,
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) {
                    emailState.shouldStartValidation = true
                } else if (!it.isFocused && emailState.shouldStartValidation) {
                    passwordState.shouldStartValidation = true
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onLogInSubmitted(emailState.email, passwordState.password)
                }
            )
        )
        Button(
            onClick = {
                keyboardController?.hide()
                onLogInSubmitted(emailState.email, passwordState.password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = stringResource(id = utilR.string.log_in))
        }
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
        onValidateEmail = { _ -> EmailError.EmptyField },
        shouldValidateImmediately = true
    )

    val passwordState = PasswordState(
        onValidatePassword = { _ -> PasswordError.EmptyField },
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
                emailAuthError = EmailError.NoExistingAccount,
                passwordAuthError = PasswordError.WrongPassword
            ),
            emailState = EmailState(onValidateEmail = { _ -> null }),
            passwordState = PasswordState(onValidatePassword = { _ -> null }),
            onLogInSubmitted = { _, _ -> },
            onNavigateToSignUp = { }
        )
    }
}
