package com.mitch.safevault.core.ui.component

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
open class TextFieldState(
    initialText: String = ""
) {
    var text by mutableStateOf(initialText)
        private set

    fun update(text: String) {
        this.text = text
    }
}

class PasswordTextFieldState(
    initialText: String = ""
) : TextFieldState(initialText) {
    var isPasswordVisible by mutableStateOf(false)
        private set

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }
}
