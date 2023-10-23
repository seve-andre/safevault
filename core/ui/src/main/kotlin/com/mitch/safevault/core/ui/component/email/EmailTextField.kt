package com.mitch.safevault.core.ui.component.email

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mitch.safevault.core.util.validator.email.EmailError

@Composable
fun EmailTextField(
    emailState: EmailState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val emailErrorMessage = emailState.error?.toErrorMessage() ?: ""

    OutlinedTextField(
        value = emailState.email,
        onValueChange = { emailState.email = it },
        modifier = modifier.semantics {
            if (emailState.error != null) error(emailErrorMessage)
        },
        label = { Text(text = "Email") },
        placeholder = { Text("example@gmail.com") },
        supportingText = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emailErrorMessage
            )
        },
        isError = emailState.error != null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        singleLine = true
    )
}

@Composable
private fun EmailError.toErrorMessage(): String {
    return when (this) {
        EmailError.EmptyField -> "Inserire la email!"
        EmailError.NoMatch -> "La email inserita non è corretta"
    }
}
