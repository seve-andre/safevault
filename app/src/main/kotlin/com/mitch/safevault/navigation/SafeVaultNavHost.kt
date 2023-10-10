package com.mitch.safevault.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mitch.safevault.feature.items.navigation.itemsNavigationRoute
import com.mitch.safevault.feature.items.navigation.itemsScreen
import com.mitch.safevault.ui.SafeVaultAppState

@Composable
fun SafeVaultNavHost(
    appState: SafeVaultAppState,
    modifier: Modifier = Modifier,
    startDestination: String = itemsNavigationRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        itemsScreen()
    }
}
