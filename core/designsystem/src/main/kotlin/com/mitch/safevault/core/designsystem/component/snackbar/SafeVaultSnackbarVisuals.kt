package com.mitch.safevault.core.designsystem.component.snackbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mitch.safevault.core.designsystem.theme.extendedColorScheme

data class SafeVaultSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val type: SafeVaultSnackbarType = SafeVaultSnackbarType.Default
) : SnackbarVisuals

enum class SafeVaultSnackbarType {
    Default,
    Success,
    Warning,
    Error
}

data class SafeVaultSnackbarColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val actionColor: Color
)

object SafeVaultSnackbarDefaults {

    @Composable
    fun defaultSnackbarColors(): SafeVaultSnackbarColors {
        return SafeVaultSnackbarColors(
            backgroundColor = SnackbarDefaults.color,
            contentColor = SnackbarDefaults.contentColor,
            actionColor = SnackbarDefaults.actionColor
        )
    }

    @Composable
    fun successSnackbarColors(): SafeVaultSnackbarColors {
        return SafeVaultSnackbarColors(
            backgroundColor = MaterialTheme.extendedColorScheme.success,
            contentColor = MaterialTheme.extendedColorScheme.onSuccess,
            actionColor = MaterialTheme.extendedColorScheme.onSuccess
        )
    }

    @Composable
    fun warningSnackbarColors(): SafeVaultSnackbarColors {
        return SafeVaultSnackbarColors(
            backgroundColor = MaterialTheme.extendedColorScheme.warning,
            contentColor = MaterialTheme.extendedColorScheme.onWarning,
            actionColor = MaterialTheme.extendedColorScheme.onWarning
        )
    }

    @Composable
    fun errorSnackbarColors(): SafeVaultSnackbarColors {
        return SafeVaultSnackbarColors(
            backgroundColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            actionColor = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}
