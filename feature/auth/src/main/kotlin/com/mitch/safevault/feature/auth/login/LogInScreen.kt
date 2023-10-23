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
import com.mitch.safevault.core.ui.component.email.EmailTextField
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.password.PasswordState
import com.mitch.safevault.core.ui.component.password.PasswordTextField
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError

@Composable
internal fun LogInRoute(
    viewModel: LogInViewModel = hiltViewModel()
) {
    LogInScreen(
        emailState = viewModel.emailState,
        passwordState = viewModel.passwordState
    )
}

@Composable
internal fun LogInScreen(
    emailState: EmailState,
    passwordState: PasswordState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailTextField(emailState = emailState)
        PasswordTextField(passwordState = passwordState)
    }
}
