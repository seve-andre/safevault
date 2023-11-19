package com.mitch.safevault.feature.auth.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import com.mitch.safevault.feature.auth.EmailState
import com.mitch.safevault.feature.auth.PasswordSignUpState
import com.mitch.safevault.feature.auth.R
import com.mitch.safevault.feature.auth.signup.component.AlreadyExistingAccountErrorCard
import com.mitch.safevault.feature.auth.signup.component.SignUpForm
import com.mitch.safevault.core.util.R as utilR

@Composable
internal fun SignUpRoute(
    onNavigateToLogIn: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val emailState by viewModel.emailState.collectAsStateWithLifecycle()
    val passwordState by viewModel.passwordState.collectAsStateWithLifecycle()
    val signUpUiState by viewModel.signUpUiState.collectAsStateWithLifecycle()

    SignUpScreen(
        signUpUiState = signUpUiState,
        emailState = emailState,
        passwordState = passwordState,
        onStartEmailValidation = viewModel::startEmailValidation,
        onStartPasswordValidation = viewModel::startPasswordValidation,
        onSignUpSubmitted = viewModel::signUp,
        onNavigateToLogIn = onNavigateToLogIn
    )
}

@Composable
internal fun SignUpScreen(
    signUpUiState: SignUpUiState,
    emailState: EmailState,
    passwordState: PasswordSignUpState,
    onStartEmailValidation: () -> Unit,
    onStartPasswordValidation: () -> Unit,
    onSignUpSubmitted: () -> Unit,
    onNavigateToLogIn: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(padding.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (signUpUiState is SignUpUiState.AuthenticationFailed) {
            AnimatedVisibility(visible = signUpUiState.emailAuthError is EmailError.Auth.AlreadyExistingAccount) {
                AlreadyExistingAccountErrorCard(onNavigateToLogIn = onNavigateToLogIn)
            }
            Spacer(modifier = Modifier.height(padding.medium))
        }

        Text(
            text = stringResource(id = utilR.string.sign_up),
            modifier = Modifier.align(Alignment.Start),
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(padding.medium))
        SignUpForm(
            emailState = emailState,
            passwordState = passwordState,
            onStartEmailValidation = onStartEmailValidation,
            onStartPasswordValidation = onStartPasswordValidation,
            onSubmit = {
                keyboardController?.hide()
                onSignUpSubmitted()
            }
        )
        Spacer(modifier = Modifier.height(padding.small))
        TextButton(
            onClick = onNavigateToLogIn,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.already_have_an_account_question))
                    append(" ")
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(id = utilR.string.log_in))
                    }
                    append("!")
                }
            )
        }
    }
}



@Preview
@Composable
private fun SignUpScreenIdlePreview() {
    SafeVaultMaterialTheme {
        SignUpScreen(
            signUpUiState = SignUpUiState.Idle,
            emailState = EmailState(
                textFieldState = TextFieldState(),
                validationError = null
            ),
            passwordState = PasswordSignUpState(
                textFieldState = PasswordTextFieldState(),
                validationErrors = null
            ),
            onStartEmailValidation = { },
            onStartPasswordValidation = { },
            onSignUpSubmitted = { },
            onNavigateToLogIn = { }
        )
    }
}

@ThemePreviews
@Composable
private fun LogInScreenAuthErrorPreview() {
    SafeVaultMaterialTheme {
        SignUpScreen(
            signUpUiState = SignUpUiState.AuthenticationFailed(
                emailAuthError = EmailError.Auth.AlreadyExistingAccount
            ),
            emailState = EmailState(
                textFieldState = TextFieldState("andrea.severi.dev@gmail.com"),
                validationError = null
            ),
            passwordState = PasswordSignUpState(
                textFieldState = PasswordTextFieldState("Abcewefiew67#"),
                validationErrors = null
            ),
            onStartEmailValidation = { },
            onStartPasswordValidation = { },
            onSignUpSubmitted = { },
            onNavigateToLogIn = { }
        )
    }
}
