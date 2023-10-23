package com.mitch.safevault.core.ui.component.email

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mitch.safevault.core.util.validator.email.EmailError

@Stable
class EmailState(
    private val onValidateEmail: (String) -> EmailError?
) {
    var email by mutableStateOf("")
    val error by derivedStateOf { onValidateEmail(email) }
}
