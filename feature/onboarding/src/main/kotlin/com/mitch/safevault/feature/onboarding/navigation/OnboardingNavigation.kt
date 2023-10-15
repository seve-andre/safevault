package com.mitch.safevault.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mitch.safevault.feature.onboarding.OnboardingRoute

const val onboardingGraphRoute = "onboarding_graph"
private const val ONBOARDING_NAVIGATION_ROUTE = "onboarding"

fun NavController.navigateToOnboardingGraph(navOptions: NavOptions? = null) {
    this.navigate(onboardingGraphRoute, navOptions)
}

fun NavGraphBuilder.onboardingGraph(
    onNavigateToSignup: () -> Unit,
    onNavigateToLogin: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        startDestination = ONBOARDING_NAVIGATION_ROUTE,
        route = onboardingGraphRoute
    ) {
        composable(route = ONBOARDING_NAVIGATION_ROUTE) {
            OnboardingRoute(
                onNavigateToSignup = onNavigateToSignup,
                onNavigateToLogin = onNavigateToLogin
            )
        }
        nestedGraphs()
    }
}
