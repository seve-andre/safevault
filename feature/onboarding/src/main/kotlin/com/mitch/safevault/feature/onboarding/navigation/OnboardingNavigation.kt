package com.mitch.safevault.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation

private const val ONBOARDING_GRAPH_ROUTE_PATTERN = "onboarding_graph"
const val onboardingNavigationRoute = "onboarding"

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    this.navigate(onboardingNavigationRoute, navOptions)
}

fun NavGraphBuilder.onboardingGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        startDestination = onboardingNavigationRoute,
        route = ONBOARDING_GRAPH_ROUTE_PATTERN
    ) {
        composable(route = onboardingNavigationRoute) {
        }
        nestedGraphs()
    }
}
