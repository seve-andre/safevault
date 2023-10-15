package com.mitch.safevault.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val signUpNavigationRoute = "signUp"
const val loginNavigationRoute = "login"

fun NavController.navigateToSignup(navOptions: NavOptions? = null) {
    this.navigate(signUpNavigationRoute, navOptions)
}

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(loginNavigationRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen() {
    composable(route = signUpNavigationRoute) {
    }
}

fun NavGraphBuilder.loginScreen() {
    composable(route = loginNavigationRoute) {
    }
}
