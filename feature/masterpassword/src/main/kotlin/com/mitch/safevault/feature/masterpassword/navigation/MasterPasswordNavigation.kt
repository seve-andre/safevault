package com.mitch.safevault.feature.masterpassword.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val masterPasswordNavigationRoute = "masterPassword"

fun NavController.navigateToMasterPassword(navOptions: NavOptions? = null) {
    this.navigate(masterPasswordNavigationRoute, navOptions)
}

fun NavGraphBuilder.masterPasswordScreen() {
    composable(route = masterPasswordNavigationRoute) {
    }
}
