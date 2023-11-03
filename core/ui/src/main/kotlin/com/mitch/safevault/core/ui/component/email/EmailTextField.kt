package com.mitch.safevault.core.ui.component.email

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mitch.safevault.core.ui.R
import com.mitch.safevault.core.ui.component.TextFieldState
import com.mitch.safevault.core.util.validator.email.EmailError

@Composable
fun EmailTextField(
    emailState: TextFieldState,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    OutlinedTextField(
        value = emailState.text,
        onValueChange = emailState::update,
        modifier = modifier.fillMaxWidth(),
        readOnly = readOnly,
        label = { Text(text = stringResource(id = R.string.email_label)) },
        placeholder = { Text("example@gmail.com") },
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = true,
        interactionSource = interactionSource
    )
}

@Composable
private fun EmailError.Validation.toErrorMessage(): String {
    return when (this) {
        EmailError.Validation.EmptyField -> stringResource(id = R.string.email_error_empty_field)
        EmailError.Validation.NotAnEmail -> stringResource(id = R.string.email_error_not_valid)
    }
}
