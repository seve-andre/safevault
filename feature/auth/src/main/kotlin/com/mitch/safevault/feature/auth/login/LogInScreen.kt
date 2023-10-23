package com.mitch.safevault.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.email.EmailTextField
import com.mitch.safevault.core.ui.component.password.PasswordState
import com.mitch.safevault.core.ui.component.password.PasswordTextField

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
