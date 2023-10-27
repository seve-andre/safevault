package com.mitch.safevault.core.designsystem.component.snackbar

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SafeVaultSnackbar(
    message: String,
    action: String?,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    Snackbar(
        modifier = modifier
    ) {
        Row {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null)
            }
            Text(text = message)
            if (action != null) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = action)
                }
            }
        }
    }
}
