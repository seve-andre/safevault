package com.mitch.safevault.core.ui.component.password

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.password
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.core.ui.R
import com.mitch.safevault.core.ui.ThemePreviews
import com.mitch.safevault.core.ui.component.PasswordTextFieldState

@Composable
fun PasswordTextField(
    passwordState: PasswordTextFieldState,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    additionalTrailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    OutlinedTextField(
        value = passwordState.text,
        onValueChange = passwordState::update,
        modifier = modifier
            .semantics {
                password()
                // if (passwordState.validationError != null) error(passwordErrorMessage)
            }
            .fillMaxWidth(),
        readOnly = readOnly,
        label = { Text(text = "Password") },
        placeholder = { Text(stringResource(id = R.string.password_placeholder)) },
        trailingIcon = {
            if (additionalTrailingIcon != null) {
                Row {
                    PasswordVisibilityIcon(passwordState)
                    additionalTrailingIcon()
                }
            } else {
                PasswordVisibilityIcon(passwordState)
            }
        },
        supportingText = supportingText,
        isError = isError,
        visualTransformation = if (passwordState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = true,
        interactionSource = interactionSource
    )
}

@Composable
private fun PasswordVisibilityIcon(passwordState: PasswordTextFieldState) {
    IconButton(onClick = passwordState::togglePasswordVisibility) {
        val visibilityIcon =
            if (passwordState.isPasswordVisible) SafeVaultIcons.EyeOff else SafeVaultIcons.Eye
        // Please provide localized description for accessibility services
        val description = if (passwordState.isPasswordVisible) {
            stringResource(id = R.string.hide_password)
        } else {
            stringResource(id = R.string.show_password)
        }
        Icon(imageVector = visibilityIcon, contentDescription = description)
    }
}

@ThemePreviews
@Composable
private fun PasswordTextFieldMultipleTrailingIconsPreview() {
    PasswordTextField(
        passwordState = PasswordTextFieldState(),
        additionalTrailingIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = SafeVaultIcons.Copy, contentDescription = null)
            }
        }
    )
}

@ThemePreviews
@Composable
private fun PasswordTextFieldVisibilityPreview() {
    val state = PasswordTextFieldState("Preview")
    state.togglePasswordVisibility()

    PasswordTextField(passwordState = state)
}
