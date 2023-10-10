package com.mitch.safevault.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val signupNavigationRoute = "signup"
const val loginNavigationRoute = "login"

fun NavController.navigateToSignup(navOptions: NavOptions? = null) {
    this.navigate(signupNavigationRoute, navOptions)
}

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginNavigationRoute, navOptions)
}

fun NavGraphBuilder.signupScreen(
    onSignUpClick: () -> Unit,
) {
    composable(route = signupNavigationRoute) {
    }
}

fun NavGraphBuilder.loginScreen(
    onLoginClick: () -> Unit
) {
    composable(route = loginNavigationRoute) {
    }
}
