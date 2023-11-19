package com.mitch.safevault.feature.auth

import com.mitch.safevault.core.ui.component.PasswordTextFieldState
import com.mitch.safevault.core.util.validator.password.PasswordError

data class PasswordLogInState(
    val textFieldState: PasswordTextFieldState,
    val validationError: PasswordError.Validation.EmptyField?
)
