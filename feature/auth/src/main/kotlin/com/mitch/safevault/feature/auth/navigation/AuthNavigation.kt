package com.mitch.safevault.feature.auth.navigation

import androidx.compose.material3.SnackbarResult
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mitch.safevault.feature.auth.login.LogInRoute

const val signUpNavigationRoute = "signUp"
const val logInNavigationRoute = "logIn"

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpNavigationRoute, navOptions)
}

fun NavController.navigateToLogIn(navOptions: NavOptions? = null) {
    this.navigate(logInNavigationRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen() {
    composable(route = signUpNavigationRoute) {
    }
}

fun NavGraphBuilder.logInScreen(
    onShowSnackbar: suspend (String, String?) -> SnackbarResult,
    onNavigateToSignUp: () -> Unit
) {
    composable(route = logInNavigationRoute) {
        LogInRoute(
            onShowSnackbar = onShowSnackbar,
            onNavigateToSignUp = onNavigateToSignUp
        )
    }
}
