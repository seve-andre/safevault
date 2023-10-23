package com.mitch.safevault.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError

@Composable
internal fun LogInRoute(
    viewModel: LogInViewModel = hiltViewModel()
) {
    val emailError = viewModel.emailError
    val passwordError = viewModel.passwordError

    LogInScreen(
        email = viewModel.email,
        emailError = emailError,
        onChangeEmail = viewModel::updateEmail,
        password = viewModel.password,
        passwordError = passwordError,
        onChangePassword = viewModel::updatePassword
    )
}

@Composable
internal fun LogInScreen(
    email: String,
    emailError: EmailError?,
    onChangeEmail: (String) -> Unit,
    password: String,
    passwordError: PasswordError.EmptyField?,
    onChangePassword: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val emailErrorMessage = emailError?.toErrorMessage() ?: ""

        TextField(
            value = email,
            onValueChange = onChangeEmail,
            modifier = Modifier.semantics {
                // Provide localized description of the error
                if (passwordError != null) error(emailErrorMessage)
            },
            label = { Text(text = "Email") },
            placeholder = { Text("example@gmail.com") },
            supportingText = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = emailErrorMessage
                )
            },
            isError = emailError != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )


        // Password
        var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
        val passwordErrorMessage = if (passwordError != null) "Inserisci la password!" else ""

        TextField(
            value = password,
            onValueChange = onChangePassword,
            modifier = Modifier.semantics {
                // Provide localized description of the error
                if (passwordError != null) error(passwordErrorMessage)
            },
            label = { Text(text = "Password") },
            placeholder = { Text("Inserisci password") },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    val visibilityIcon =
                        if (isPasswordVisible) SafeVaultIcons.EyeOff else SafeVaultIcons.Eye
                    // Please provide localized description for accessibility services
                    val description = if (isPasswordVisible) "Hide password" else "Show password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            },
            supportingText = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = passwordErrorMessage
                )
            },
            isError = passwordError != null,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    // on submit
                }
            ),
            singleLine = true
        )
    }
}

@Composable
private fun EmailError.toErrorMessage(): String {
    return when (this) {
        EmailError.EmptyField -> "Inserire la email!"
        EmailError.NoMatch -> "La email inserita non è corretta"
    }
}
