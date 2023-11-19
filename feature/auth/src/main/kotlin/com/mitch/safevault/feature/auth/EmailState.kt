package com.mitch.safevault.feature.auth

import com.mitch.safevault.core.ui.component.TextFieldState
import com.mitch.safevault.core.util.validator.email.EmailError

data class EmailState(
    val textFieldState: TextFieldState,
    val validationError: EmailError.Validation?
)
