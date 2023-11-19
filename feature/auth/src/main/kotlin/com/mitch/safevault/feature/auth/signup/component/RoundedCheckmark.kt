package com.mitch.safevault.feature.auth.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.core.designsystem.theme.extendedColorScheme

private data class CheckmarkData(
    val check: ImageVector,
    val backgroundColor: Color,
    val iconColor: Color
)

@Composable
internal fun RoundedCheckmark(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    checkmarkSize: Dp = 50.dp,
    iconSize: Dp = checkmarkSize.times(0.7f)
) {
    val checkInfo = if (isChecked) {
        CheckmarkData(
            check = SafeVaultIcons.Check,
            backgroundColor = MaterialTheme.extendedColorScheme.success,
            iconColor = MaterialTheme.extendedColorScheme.onSuccess
        )
    } else {
        CheckmarkData(
            check = SafeVaultIcons.Uncheck,
            backgroundColor = MaterialTheme.colorScheme.error,
            iconColor = MaterialTheme.colorScheme.onError
        )
    }

    Box(
        modifier = modifier
            .semantics {
                toggleableState = if (isChecked) {
                    ToggleableState.On
                } else {
                    ToggleableState.Off
                }
            }
            .size(checkmarkSize)
            .clip(CircleShape)
            .background(checkInfo.backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = checkInfo.check,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = checkInfo.iconColor
        )
    }
}

@Preview
@Composable
private fun CheckmarkOnStatePreview() {
    RoundedCheckmark(isChecked = true)
}

@Preview
@Composable
private fun CheckmarkOffStatePreview() {
    RoundedCheckmark(isChecked = false)
}
