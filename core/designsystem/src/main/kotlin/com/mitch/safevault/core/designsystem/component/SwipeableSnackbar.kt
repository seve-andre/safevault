package com.mitch.safevault.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableSnackbar(
    state: SnackbarHostState,
    modifier: Modifier = Modifier,
    dismissSnackbarState: DismissState = rememberDismissState(),
    dismissContent: @Composable RowScope.() -> Unit
) {
    LaunchedEffect(dismissSnackbarState.currentValue) {
        if (dismissSnackbarState.currentValue != DismissValue.Default) {
            state.currentSnackbarData?.dismiss()
            delay(500)
            dismissSnackbarState.snapTo(DismissValue.Default)
        }
    }
    SwipeToDismiss(
        modifier = modifier,
        state = dismissSnackbarState,
        background = {},
        dismissContent = dismissContent
    )
}
