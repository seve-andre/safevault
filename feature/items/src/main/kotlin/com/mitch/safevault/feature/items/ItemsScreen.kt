package com.mitch.safevault.feature.items

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun ItemsRoute(
    viewModel: ItemsViewModel = hiltViewModel()
) {
    ItemsScreen()
}

@Composable
internal fun ItemsScreen() {
}
