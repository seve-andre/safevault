package com.mitch.safevault

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitch.safevault.core.data.util.network.NetworkMonitor
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.core.designsystem.component.snackbar.SafeVaultSnackbar
import com.mitch.safevault.core.designsystem.component.snackbar.SafeVaultSnackbarDefaults
import com.mitch.safevault.core.designsystem.component.snackbar.SafeVaultSnackbarType
import com.mitch.safevault.core.designsystem.component.snackbar.SafeVaultSnackbarVisuals
import com.mitch.safevault.core.designsystem.component.snackbar.SwipeableSnackbar
import com.mitch.safevault.core.designsystem.theme.SafeVaultMaterialTheme
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.util.SafeVaultTheme
import com.mitch.safevault.navigation.SafeVaultNavHost
import com.mitch.safevault.ui.rememberSafeVaultAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // if not needed, also remove permission from AndroidManifest.xml
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        /* Must be called before super.onCreate()
         *
         * Splashscreen look in res/values/themes.xml
         */
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isThemeDark = shouldUseDarkTheme(uiState)

            SafeVaultMaterialTheme(isThemeDark = isThemeDark) {
                val appState = rememberSafeVaultAppState(networkMonitor)
                val isOffline by appState.isOffline.collectAsStateWithLifecycle()

                LaunchedEffect(isOffline) {
                    if (isOffline) {
                        appState.snackbarHostState.showSnackbar(
                            message = getString(R.string.not_connected),
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }

                Scaffold(
                    snackbarHost = {
                        SwipeableSnackbar(
                            state = appState.snackbarHostState,
                            dismissContent = {
                                SnackbarHost(
                                    hostState = appState.snackbarHostState,
                                    modifier = Modifier
                                        .navigationBarsPadding()
                                        .padding(horizontal = padding.medium)
                                ) {
                                    val customVisuals = it.visuals as SafeVaultSnackbarVisuals
                                    val colors = when (customVisuals.type) {
                                        SafeVaultSnackbarType.Default -> SafeVaultSnackbarDefaults.defaultSnackbarColors()
                                        SafeVaultSnackbarType.Success -> SafeVaultSnackbarDefaults.successSnackbarColors()
                                        SafeVaultSnackbarType.Warning -> SafeVaultSnackbarDefaults.warningSnackbarColors()
                                        SafeVaultSnackbarType.Error -> SafeVaultSnackbarDefaults.errorSnackbarColors()
                                    }
                                    val icon = when (customVisuals.type) {
                                        SafeVaultSnackbarType.Default -> null
                                        SafeVaultSnackbarType.Success -> SafeVaultIcons.Success
                                        SafeVaultSnackbarType.Warning -> SafeVaultIcons.Warning
                                        SafeVaultSnackbarType.Error -> SafeVaultIcons.Error
                                    }

                                    SafeVaultSnackbar(
                                        message = customVisuals.message,
                                        action = customVisuals.actionLabel,
                                        icon = icon,
                                        colors = colors
                                    )
                                }
                            }
                        )
                    }
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .safeDrawingPadding()
                    ) {
                        SafeVaultNavHost(
                            appState = appState,
                            onShowSnackbar = { visuals: SafeVaultSnackbarVisuals ->
                                appState.snackbarHostState.showSnackbar(visuals)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.theme) {
        SafeVaultTheme.Dark -> true
        SafeVaultTheme.Light -> false
        SafeVaultTheme.FollowSystem -> isSystemInDarkTheme()
    }
}
