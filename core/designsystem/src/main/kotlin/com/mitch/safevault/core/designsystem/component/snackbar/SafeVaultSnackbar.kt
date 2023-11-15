package com.mitch.safevault.core.designsystem.component.snackbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.SafeVaultIcons

@Composable
fun SafeVaultSnackbar(
    message: String,
    action: String?,
    colors: SafeVaultSnackbarColors,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Snackbar(
        modifier = modifier,
        action = { if (action != null) Text(text = action) },
        containerColor = colors.backgroundColor,
        contentColor = colors.contentColor,
        actionContentColor = colors.actionColor
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null)
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(text = message)
        }
    }
}

@Preview
@Composable
private fun SafeVaultSnackbarDefaultPreview() {
    SafeVaultSnackbar(
        message = "Default",
        action = "This is my action",
        colors = SafeVaultSnackbarDefaults.defaultSnackbarColors(),
        icon = SafeVaultIcons.ArrowRight
    )
}

@Preview
@Composable
private fun SafeVaultSnackbarSuccessPreview() {
    SafeVaultSnackbar(
        message = "Success",
        action = null,
        colors = SafeVaultSnackbarDefaults.successSnackbarColors(),
        icon = Icons.Rounded.CheckCircle
    )
}

@Preview
@Composable
private fun SafeVaultSnackbarWarningPreview() {
    SafeVaultSnackbar(
        message = "Warning",
        action = null,
        colors = SafeVaultSnackbarDefaults.warningSnackbarColors(),
        icon = Icons.Rounded.Warning
    )
}

@Preview
@Composable
private fun SafeVaultSnackbarErrorPreview() {
    SafeVaultSnackbar(
        message = "Error",
        action = null,
        colors = SafeVaultSnackbarDefaults.errorSnackbarColors(),
        icon = Icons.Rounded.Close
    )
}
