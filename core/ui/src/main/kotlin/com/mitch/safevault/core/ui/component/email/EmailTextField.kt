package com.mitch.safevault.core.ui.component.email

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mitch.safevault.core.ui.R
import com.mitch.safevault.core.util.validator.email.EmailAuthError
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.email.EmailValidationError

@Composable
fun EmailTextField(
    emailState: EmailState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val emailErrorMessage = emailState.validationError?.toErrorMessage() ?: ""

    OutlinedTextField(
        value = emailState.email,
        onValueChange = { emailState.email = it },
        modifier = modifier
            .semantics {
                if (emailState.validationError != null) error(emailErrorMessage)
            }
            .fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.email_label)) },
        placeholder = { Text("example@gmail.com") },
        supportingText = {
            if (emailState.validationError != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = emailErrorMessage
                )
            }
        },
        isError = emailState.validationError != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = true
    )
}

@Composable
private fun EmailValidationError.toErrorMessage(): String {
    return when (this) {
        EmailError.EmptyField -> stringResource(id = R.string.email_error_empty_field)
        EmailError.NotAnEmail -> stringResource(id = R.string.email_error_not_valid)
    }
}
