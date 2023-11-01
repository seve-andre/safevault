package com.mitch.safevault.core.ui.component.password

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mitch.safevault.core.util.validator.password.PasswordError

@Stable
class PasswordState(
    onValidatePassword: (String) -> PasswordError.Validation?,
    shouldValidateImmediately: Boolean = false
) {
    var password by mutableStateOf("")
    var shouldStartValidation by mutableStateOf(false)
        private set
    val validationError by derivedStateOf {
        if (shouldValidateImmediately || shouldStartValidation) {
            onValidatePassword(password)
        } else {
            null
        }
    }
    var isPasswordVisible by mutableStateOf(false)
        private set

    fun startValidation() {
        shouldStartValidation = true
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun hasError(): Boolean {
        return validationError != null
    }
}
