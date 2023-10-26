package com.mitch.safevault.core.ui.component.password

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.core.util.validator.password.PasswordValidationError

@Stable
class PasswordState(
    private val onValidatePassword: (String) -> PasswordValidationError?
) {
    var password by mutableStateOf("")
    var shouldStartValidation by mutableStateOf(false)
    val validationError by derivedStateOf { if (shouldStartValidation) onValidatePassword(password) else null }
    var isPasswordVisible by mutableStateOf(false)

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }
}
