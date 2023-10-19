package com.mitch.safevault.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mitch.safevault.feature.onboarding.OnboardingRoute

const val OnboardingGraphRoutePattern = "onboarding_graph"
private const val OnboardingNavigationRoute = "onboarding"

fun NavController.navigateToOnboardingGraph(navOptions: NavOptions? = null) {
    this.navigate(OnboardingGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.onboardingGraph(
    onNavigateToSignup: () -> Unit,
    onNavigateToLogin: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        startDestination = OnboardingNavigationRoute,
        route = OnboardingGraphRoutePattern
    ) {
        composable(route = OnboardingNavigationRoute) {
            OnboardingRoute(
                onNavigateToSignup = onNavigateToSignup,
                onNavigateToLogin = onNavigateToLogin
            )
        }
        nestedGraphs()
    }
}
