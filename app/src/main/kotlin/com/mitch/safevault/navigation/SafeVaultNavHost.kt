package com.mitch.safevault.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mitch.safevault.feature.home.navigation.homeNavigationRoute
import com.mitch.safevault.feature.home.navigation.homeScreen
import com.mitch.safevault.ui.SafeVaultAppState

@Composable
fun NiaNavHost(
    appState: SafeVaultAppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen()
    }
}
