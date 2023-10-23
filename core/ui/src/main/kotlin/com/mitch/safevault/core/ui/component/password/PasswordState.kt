package com.mitch.safevault.core.ui.component.password

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mitch.safevault.core.util.validator.password.PasswordError

@Stable
class PasswordState(
    private val onValidatePassword: (String) -> PasswordError?
) {
    var password by mutableStateOf("")
    val error by derivedStateOf { onValidatePassword(password) }
    var isPasswordVisible by mutableStateOf(false)

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }
}
