package com.mitch.safevault.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mitch.safevault.feature.auth.navigation.loginScreen
import com.mitch.safevault.feature.auth.navigation.navigateToLogin
import com.mitch.safevault.feature.auth.navigation.navigateToSignup
import com.mitch.safevault.feature.auth.navigation.signUpScreen
import com.mitch.safevault.feature.items.navigation.itemsScreen
import com.mitch.safevault.feature.masterpassword.navigation.masterPasswordScreen
import com.mitch.safevault.feature.onboarding.navigation.onboardingGraph
import com.mitch.safevault.feature.onboarding.navigation.onboardingGraphRoute
import com.mitch.safevault.ui.SafeVaultAppState

@Composable
fun SafeVaultNavHost(
    appState: SafeVaultAppState,
    modifier: Modifier = Modifier,
    startDestination: String = onboardingGraphRoute
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        onboardingGraph(
            onNavigateToSignup = navController::navigateToSignup,
            onNavigateToLogin = navController::navigateToLogin,
            nestedGraphs = {
                signUpScreen()
                loginScreen()
            }
        )
        masterPasswordScreen()
        itemsScreen()
    }
}
