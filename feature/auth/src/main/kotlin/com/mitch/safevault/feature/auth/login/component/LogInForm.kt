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
import com.mitch.safevault.core.designsystem.theme.SafeVaultMaterialTheme
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.ThemePreviews
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.email.EmailTextField
import com.mitch.safevault.core.ui.component.password.PasswordState
import com.mitch.safevault.core.ui.component.password.PasswordTextField
import com.mitch.safevault.core.util.R
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
import kotlinx.coroutines.delay

@Composable
internal fun LogInForm(
    emailState: EmailState,
    passwordState: PasswordState,
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
            emailState = emailState,
            modifier = Modifier
                .focusRequester(emailFocusRequester)
                .onFocusChanged {
                    if (it.isFocused && !isEmailBlurred) {
                        isEmailBlurred = true
                    }

                    if (!it.isFocused && isEmailBlurred) {
                        emailState.startValidation()
                    }
                }
        )
        PasswordTextField(
            passwordState = passwordState,
            modifier = Modifier.onFocusChanged {
                if (it.isFocused && !isPasswordBlurred) {
                    isPasswordBlurred = true
                }

                if (!it.isFocused && isPasswordBlurred) {
                    passwordState.startValidation()
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

@ThemePreviews
@Composable
private fun LogInFormValidationErrorsPreview() {
    val emailState = EmailState(
        onValidateEmail = { _ -> EmailError.Validation.EmptyField },
    )
    emailState.startValidation()

    val passwordState = PasswordState(
        onValidatePassword = { _ -> PasswordError.Validation.EmptyField },
    )
    passwordState.startValidation()

    SafeVaultMaterialTheme {
        LogInForm(
            emailState = emailState,
            passwordState = passwordState,
            onSubmit = { }
        )
    }
}

@ThemePreviews
@Composable
private fun LogInFormPasswordVisibilityPreview() {
    val passwordState = PasswordState(onValidatePassword = { _ -> null })
    passwordState.password = "Preview"
    passwordState.togglePasswordVisibility()

    SafeVaultMaterialTheme {
        LogInForm(
            emailState = EmailState(onValidateEmail = { _ -> null }),
            passwordState = passwordState,
            onSubmit = { }
        )
    }
}
