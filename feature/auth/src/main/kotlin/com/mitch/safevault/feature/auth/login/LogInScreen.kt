package com.mitch.safevault.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.email.EmailTextField
import com.mitch.safevault.core.ui.component.password.PasswordState
import com.mitch.safevault.core.ui.component.password.PasswordTextField
import com.mitch.safevault.feature.auth.R
import kotlinx.coroutines.delay
import com.mitch.safevault.core.util.R as utilR

@Composable
internal fun LogInRoute(
    viewModel: LogInViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit
) {
    LogInScreen(
        emailState = viewModel.emailState,
        passwordState = viewModel.passwordState,
        onLogInSubmitted = viewModel::logIn,
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun LogInScreen(
    emailState: EmailState,
    passwordState: PasswordState,
    onLogInSubmitted: (String, String) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val emailFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(1_000)
        emailFocusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailTextField(
            emailState = emailState,
            modifier = Modifier.focusRequester(emailFocusRequester)
        )
        PasswordTextField(
            passwordState = passwordState,
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) emailState.shouldStartValidation = true
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    passwordState.shouldStartValidation = true
                    onLogInSubmitted(emailState.email, passwordState.password)
                }
            )
        )
        Button(
            onClick = {
                onLogInSubmitted(emailState.email, passwordState.password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = stringResource(id = utilR.string.log_in))
        }
        TextButton(onClick = onNavigateToSignUp) {
            Text(text = stringResource(id = R.string.sign_up_now))
        }
    }
}
