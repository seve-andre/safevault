package com.mitch.safevault.core.ui.component.password

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.password
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.core.ui.R

@Composable
fun PasswordTextField(
    passwordState: PasswordState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val passwordErrorMessage = stringResource(id = R.string.password_error_empty_field)

    OutlinedTextField(
        value = passwordState.password,
        onValueChange = { passwordState.password = it },
        modifier = modifier
            .semantics {
                password()
                if (passwordState.validationError != null) error(passwordErrorMessage)
            }
            .fillMaxWidth(),
        label = { Text(text = "Password") },
        placeholder = { Text(stringResource(id = R.string.password_placeholder)) },
        trailingIcon = {
            IconButton(onClick = { passwordState.togglePasswordVisibility() }) {
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
        },
        supportingText = {
            if (passwordState.validationError != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = passwordErrorMessage
                )
            }
        },
        isError = passwordState.validationError != null,
        visualTransformation = if (passwordState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = true
    )
}
