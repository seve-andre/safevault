package com.mitch.safevault.core.util.validator.email

sealed interface EmailError {
    data object EmptyField : EmailError
    data object NoMatch : EmailError
}
