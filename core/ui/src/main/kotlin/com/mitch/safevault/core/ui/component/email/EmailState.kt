package com.mitch.safevault.core.ui.component.email

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mitch.safevault.core.util.validator.email.EmailValidationError

@Stable
class EmailState(
    private val onValidateEmail: (String) -> EmailValidationError?,
    private val shouldValidateImmediately: Boolean = false
) {
    var email by mutableStateOf("")
    val validationError by derivedStateOf {
        if (shouldValidateImmediately || shouldStartValidation) {
            onValidateEmail(email)
        } else {
            null
        }
    }
    var shouldStartValidation by mutableStateOf(false)
}
