package com.mitch.safevault.feature.auth.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.component.email.EmailTextField
import com.mitch.safevault.core.ui.component.password.PasswordTextField
import com.mitch.safevault.core.util.validator.password.PasswordConstraints
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.feature.auth.EmailState
import com.mitch.safevault.feature.auth.PasswordSignUpState
import com.mitch.safevault.feature.auth.login.component.toErrorMessage
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import com.mitch.safevault.core.ui.R as uiR
import com.mitch.safevault.core.util.R as utilR

@Composable
internal fun SignUpForm(
    emailState: EmailState,
    passwordState: PasswordSignUpState,
    onStartEmailValidation: () -> Unit,
    onStartPasswordValidation: () -> Unit,
    onSubmit: () -> Unit
) {
    val emailFocusRequester = remember { FocusRequester() }
    var isEmailBlurred by remember { mutableStateOf(false) }
    var isPasswordBlurred by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1.seconds)
        emailFocusRequester.requestFocus()
    }

    Column(verticalArrangement = Arrangement.spacedBy(padding.medium)) {
        val emailErrorMessage = emailState.validationError?.toErrorMessage()
        EmailTextField(
            emailState = emailState.textFieldState,
            modifier = Modifier
                .semantics {
                    if (emailErrorMessage != null) error(emailErrorMessage)
                }
                .focusRequester(emailFocusRequester)
                .onFocusChanged {
                    if (it.isFocused && !isEmailBlurred) {
                        isEmailBlurred = true
                    }

                    if (!it.isFocused && isEmailBlurred) {
                        onStartEmailValidation()
                    }
                },
            isError = emailErrorMessage != null,
            supportingText = {
                if (emailErrorMessage != null) {
                    Text(text = emailErrorMessage)
                }
            }
        )

        val passwordErrorMessages = passwordState.validationErrors?.map {
            if (it is PasswordError.Validation.EmptyField) {
                it.toErrorMessage()
            } else {
                stringResource(
                    id = uiR.string.password_must_have_at_least,
                    it.toErrorMessage()
                )
            }
        }
        PasswordTextField(
            passwordState = passwordState.textFieldState,
            modifier = Modifier
                .semantics {
                    if (passwordErrorMessages != null) {
                        error(passwordErrorMessages[0])
                    }
                }
                .onFocusChanged {
                    if (it.isFocused && !isPasswordBlurred) {
                        isPasswordBlurred = true
                        onStartPasswordValidation()
                    }
                },
            supportingText = {
                val checks = setOf(
                    PasswordError.Validation.NoUppercaseLetter,
                    PasswordError.Validation.NoLowercaseLetter,
                    PasswordError.Validation.InputTooShort,
                    PasswordError.Validation.NoNumber,
                    PasswordError.Validation.NoSpecialCharacter
                )
                Column {
                    checks.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .semantics {
                                    contentDescription = it.toString()
                                },
                            horizontalArrangement = Arrangement.spacedBy(padding.medium),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val isChecked = when {
                                passwordState.textFieldState.text.isBlank() -> false
                                passwordState.textFieldState.text.isNotBlank() && passwordState.validationErrors == null -> true
                                else -> !passwordState.validationErrors!!.contains(it)
                            }

                            RoundedCheckmark(
                                isChecked = isChecked,
                                checkmarkSize = 25.dp
                            )
                            Text(
                                text = it.toErrorMessage(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (isChecked) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            },
            isError = passwordState.validationErrors != null,
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
            Text(text = stringResource(id = utilR.string.sign_up))
        }
    }
}

@Composable
internal fun PasswordError.Validation.toErrorMessage(): String {
    return when (this) {
        PasswordError.Validation.EmptyField -> stringResource(id = uiR.string.password_error_empty_field)
        PasswordError.Validation.InputTooShort -> stringResource(id = uiR.string.password_error_input_too_short)
        PasswordError.Validation.NoLowercaseLetter -> stringResource(id = uiR.string.password_error_no_lowercase_letter)
        PasswordError.Validation.NoUppercaseLetter -> stringResource(id = uiR.string.password_error_no_uppercase_letter)
        PasswordError.Validation.NoNumber -> stringResource(id = uiR.string.password_error_no_number)
        PasswordError.Validation.NoSpecialCharacter -> stringResource(
            id = uiR.string.password_error_no_special_character,
            PasswordConstraints.SPECIAL_CHARACTERS
        )
    }
}
