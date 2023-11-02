package com.mitch.safevault.core.ui.component.email

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mitch.safevault.core.util.validator.email.EmailError

@Stable
class EmailState(onValidateEmail: (String) -> EmailError.Validation?) {
    var email by mutableStateOf("")
    val validationError by derivedStateOf {
        if (shouldStartValidation) {
            onValidateEmail(email)
        } else {
            null
        }
    }
    var shouldStartValidation by mutableStateOf(false)
        private set

    fun startValidation() {
        shouldStartValidation = true
    }

    fun hasError(): Boolean {
        return validationError != null
    }
}
