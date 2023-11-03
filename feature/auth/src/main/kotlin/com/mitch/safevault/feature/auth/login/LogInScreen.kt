package com.mitch.safevault.feature.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.safevault.core.designsystem.theme.SafeVaultMaterialTheme
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.ThemePreviews
import com.mitch.safevault.core.ui.component.PasswordTextFieldState
import com.mitch.safevault.core.ui.component.TextFieldState
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.feature.auth.R
import com.mitch.safevault.feature.auth.login.component.LogInForm
import com.mitch.safevault.feature.auth.login.component.NoExistingAccountErrorCard
import com.mitch.safevault.core.util.R as utilR

@Composable
internal fun LogInRoute(
    viewModel: LogInViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit
) {
    val emailState by viewModel.emailState.collectAsStateWithLifecycle()
    val passwordState by viewModel.passwordState.collectAsStateWithLifecycle()
    val logInUiState by viewModel.logInUiState.collectAsStateWithLifecycle()

    LogInScreen(
        logInUiState = logInUiState,
        emailState = emailState,
        passwordState = passwordState,
        onStartEmailValidation = viewModel::startEmailValidation,
        onStartPasswordValidation = viewModel::startPasswordValidation,
        onLogInSubmitted = viewModel::logIn,
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@Composable
internal fun LogInScreen(
    logInUiState: LogInUiState,
    emailState: EmailState,
    passwordState: PasswordState,
    onStartEmailValidation: () -> Unit,
    onStartPasswordValidation: () -> Unit,
    onLogInSubmitted: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (logInUiState is LogInUiState.AuthenticationFailed) {
            AnimatedVisibility(visible = logInUiState.emailAuthError is EmailError.Auth.NoExistingAccount) {
                NoExistingAccountErrorCard(onNavigateToSignUp = onNavigateToSignUp)
            }

            if (logInUiState.passwordAuthError != null) {
                Text(text = stringResource(id = R.string.password_error_wrong))
            }
        }
        Text(
            text = stringResource(id = utilR.string.log_in),
            modifier = Modifier.align(Alignment.Start),
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(padding.medium))
        LogInForm(
            emailState = emailState,
            passwordState = passwordState,
            onStartEmailValidation = onStartEmailValidation,
            onStartPasswordValidation = onStartPasswordValidation,
            onSubmit = {
                keyboardController?.hide()
                onLogInSubmitted()
            }
        )
        Spacer(modifier = Modifier.height(padding.small))
        TextButton(
            onClick = onNavigateToSignUp,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.no_account_question))
                    append(" ")
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(id = R.string.sign_up_now))
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun LogInScreenIdlePreview() {
    SafeVaultMaterialTheme {
        LogInScreen(
            logInUiState = LogInUiState.Idle,
            emailState = EmailState(
                textFieldState = TextFieldState(),
                validationError = null
            ),
            passwordState = PasswordState(
                textFieldState = PasswordTextFieldState(),
                validationError = null
            ),
            onStartEmailValidation = { },
            onStartPasswordValidation = { },
            onLogInSubmitted = { },
            onNavigateToSignUp = { }
        )
    }
}

@ThemePreviews
@Composable
private fun LogInScreenAuthErrorPreview() {
    SafeVaultMaterialTheme {
        LogInScreen(
            logInUiState = LogInUiState.AuthenticationFailed(
                emailAuthError = EmailError.Auth.NoExistingAccount,
                passwordAuthError = PasswordError.Auth.WrongPassword
            ),
            emailState = EmailState(
                textFieldState = TextFieldState("andrea.severi.dev@gmail.com"),
                validationError = null
            ),
            passwordState = PasswordState(
                textFieldState = PasswordTextFieldState("Abcewefiew67#"),
                validationError = null
            ),
            onStartEmailValidation = { },
            onStartPasswordValidation = { },
            onLogInSubmitted = { },
            onNavigateToSignUp = { }
        )
    }
}
