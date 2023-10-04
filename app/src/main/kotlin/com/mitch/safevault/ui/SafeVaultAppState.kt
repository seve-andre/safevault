package com.mitch.safevault.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mitch.safevault.ui.util.SnackbarController
import com.mitch.safevault.core.data.util.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberSafeVaultAppState(
    networkMonitor: NetworkMonitor,
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): SafeVaultAppState {
    return remember(navController, snackbarHostState, coroutineScope, networkMonitor) {
        SafeVaultAppState(navController, snackbarHostState, coroutineScope, networkMonitor)
    }
}

// Controls app state. Stable -> if any of the values is changed, the Composables are recomposed
@Stable
class SafeVaultAppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
    val snackbarController: SnackbarController = SnackbarController(
        snackbarHostState,
        coroutineScope
    )
) {
    /**
     * App's current [Destination] if set, otherwise starting destination.
     *
     * Starting destination: search for `@RootNavGraph(start = true)`
     */
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    /**
     * Manages app connectivity status
     */
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
}
