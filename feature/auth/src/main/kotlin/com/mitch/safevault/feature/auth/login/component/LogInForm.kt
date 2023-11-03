package com.mitch.safevault.feature.auth.login.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.ThemePreviews
import com.mitch.safevault.core.ui.component.PasswordTextFieldState
import com.mitch.safevault.core.ui.component.TextFieldState
import com.mitch.safevault.core.ui.component.email.EmailTextField
import com.mitch.safevault.core.ui.component.password.PasswordTextField
import com.mitch.safevault.core.util.R
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.feature.auth.login.EmailState
import com.mitch.safevault.feature.auth.login.PasswordState
import kotlinx.coroutines.delay

@Composable
internal fun LogInForm(
    emailState: EmailState,
    passwordState: PasswordState,
    onStartEmailValidation: () -> Unit,
    onStartPasswordValidation: () -> Unit,
    onSubmit: () -> Unit
) {
    val emailFocusRequester = remember { FocusRequester() }
    var isEmailBlurred by remember { mutableStateOf(false) }

    var isPasswordBlurred by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1_000)
        emailFocusRequester.requestFocus()
    }

    Column(verticalArrangement = Arrangement.spacedBy(padding.medium)) {
        EmailTextField(
            emailState = emailState.textFieldState,
            modifier = Modifier
                .focusRequester(emailFocusRequester)
                .onFocusChanged {
                    if (it.isFocused && !isEmailBlurred) {
                        isEmailBlurred = true
                    }

                    if (!it.isFocused && isEmailBlurred) {
                        onStartEmailValidation()
                    }
                },
            isError = emailState.validationError != null,
            supportingText = {
                if (emailState.validationError != null) {
                    Text(text = emailState.validationError.toErrorMessage())
                }
            }
        )
        PasswordTextField(
            passwordState = passwordState.textFieldState,
            modifier = Modifier.onFocusChanged {
                if (it.isFocused && !isPasswordBlurred) {
                    isPasswordBlurred = true
                }

                if (!it.isFocused && isPasswordBlurred) {
                    onStartPasswordValidation()
                }
            },
            isError = passwordState.validationError != null,
            supportingText = {
                if (passwordState.validationError != null) {
                    Text(text = stringResource(id = com.mitch.safevault.core.ui.R.string.password_error_empty_field))
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    onSubmit()
                }
            )
        )
        Button(
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = stringResource(id = R.string.log_in))
        }
    }
}

@Composable
private fun EmailError.Validation.toErrorMessage(): String {
    return when (this) {
        EmailError.Validation.EmptyField -> stringResource(id = com.mitch.safevault.core.ui.R.string.email_error_empty_field)
        EmailError.Validation.NotAnEmail -> stringResource(id = com.mitch.safevault.core.ui.R.string.email_error_not_valid)
    }
}

@ThemePreviews
@Composable
private fun LogInFormFormErrorsPreview() {
    LogInForm(
        emailState = EmailState(
            textFieldState = TextFieldState(),
            validationError = EmailError.Validation.EmptyField
        ),
        passwordState = PasswordState(
            textFieldState = PasswordTextFieldState(),
            validationError = PasswordError.Validation.EmptyField
        ),
        onStartEmailValidation = { /*TODO*/ },
        onStartPasswordValidation = { /*TODO*/ }
    ) {
    }
}
