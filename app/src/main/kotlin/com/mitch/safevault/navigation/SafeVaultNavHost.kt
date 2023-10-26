package com.mitch.safevault.navigation

import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mitch.safevault.feature.auth.navigation.logInScreen
import com.mitch.safevault.feature.auth.navigation.navigateToLogIn
import com.mitch.safevault.feature.auth.navigation.navigateToSignUp
import com.mitch.safevault.feature.auth.navigation.signUpScreen
import com.mitch.safevault.feature.items.navigation.itemsScreen
import com.mitch.safevault.feature.masterpassword.navigation.masterPasswordScreen
import com.mitch.safevault.feature.onboarding.navigation.OnboardingGraphRoutePattern
import com.mitch.safevault.feature.onboarding.navigation.onboardingGraph
import com.mitch.safevault.ui.SafeVaultAppState

@Composable
fun SafeVaultNavHost(
    appState: SafeVaultAppState,
    onShowSnackbar: suspend (String, String?) -> SnackbarResult,
    modifier: Modifier = Modifier,
    startDestination: String = OnboardingGraphRoutePattern
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        onboardingGraph(
            onNavigateToSignup = navController::navigateToSignUp,
            onNavigateToLogin = navController::navigateToLogIn,
            nestedGraphs = {
                signUpScreen()
                logInScreen(onNavigateToSignUp = navController::navigateToSignUp)
            }
        )
        masterPasswordScreen()
        itemsScreen()
    }
}
