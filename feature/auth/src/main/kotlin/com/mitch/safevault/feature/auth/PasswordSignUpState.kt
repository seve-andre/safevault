package com.mitch.safevault.feature.auth

import com.mitch.safevault.core.ui.component.PasswordTextFieldState
import com.mitch.safevault.core.util.validator.password.PasswordError

data class PasswordSignUpState(
    val textFieldState: PasswordTextFieldState,
    val validationErrors: List<PasswordError.Validation>?
)
