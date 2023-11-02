package com.mitch.safevault.feature.auth.login

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.mitch.safevault.core.designsystem.theme.SafeVaultMaterialTheme
import com.mitch.safevault.core.testing.util.getStringById
import com.mitch.safevault.core.ui.component.email.EmailState
import com.mitch.safevault.core.ui.component.password.PasswordState
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
import com.mitch.safevault.feature.auth.R
import com.mitch.safevault.feature.auth.navigation.logInNavigationRoute
import com.mitch.safevault.feature.auth.navigation.navigateToSignUp
import com.mitch.safevault.feature.auth.navigation.signUpNavigationRoute
import com.mitch.safevault.feature.auth.navigation.signUpScreen
import org.junit.Rule
import org.junit.Test
import com.mitch.safevault.core.ui.R as uiR

class LogInScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Test
    fun logInForm_shouldBeIdleAtFirst() {
        composeTestRule.setContent {
            SafeVaultMaterialTheme {
                LogInScreen(
                    logInUiState = LogInUiState.Idle,
                    emailState = EmailState(onValidateEmail = { _ -> EmailError.Validation.EmptyField }),
                    passwordState = PasswordState(onValidatePassword = { _ -> PasswordError.Validation.EmptyField }),
                    onLogInSubmitted = { },
                    onNavigateToSignUp = { }
                )
            }
        }

        composeTestRule
            .onError(uiR.string.email_error_empty_field)
            .assertDoesNotExist()

        composeTestRule
            .onError(uiR.string.password_error_empty_field)
            .assertDoesNotExist()

        composeTestRule
            .onNoExistingAccountErrorCard()
            .assertDoesNotExist()
    }

    @Test
    fun logInForm_whenNoExistingAccountAuthError_shouldDisplayErrorCard() {
        composeTestRule.setContent {
            SafeVaultMaterialTheme {
                LogInScreen(
                    logInUiState = LogInUiState.AuthenticationFailed(emailAuthError = EmailError.Auth.NoExistingAccount),
                    emailState = EmailState(onValidateEmail = { _ -> null }),
                    passwordState = PasswordState(onValidatePassword = { _ -> null }),
                    onLogInSubmitted = { },
                    onNavigateToSignUp = { }
                )
            }
        }

        composeTestRule
            .onNoExistingAccountErrorCard()
            .assertIsDisplayed()
    }

    @Test
    fun logInForm_whenFormFieldsHaveErrors_shouldDisplayErrors() {
        val emailState = EmailState(
            onValidateEmail = { _ -> EmailError.Validation.EmptyField },
        )
        emailState.startValidation()

        val passwordState = PasswordState(
            onValidatePassword = { _ -> PasswordError.Validation.EmptyField },
        )
        passwordState.startValidation()

        composeTestRule.setContent {
            SafeVaultMaterialTheme {
                LogInScreen(
                    logInUiState = LogInUiState.Idle,
                    emailState = emailState,
                    passwordState = passwordState,
                    onLogInSubmitted = { },
                    onNavigateToSignUp = { }
                )
            }
        }

        composeTestRule
            .onError(uiR.string.email_error_empty_field)
            .assertIsDisplayed()

        composeTestRule
            .onError(uiR.string.password_error_empty_field)
            .assertIsDisplayed()
    }

    @Test
    fun logInForm_whenClickingOnSignUp_shouldNavigateToSignUpScreen() {
        composeTestRule.setContent {
            navController = createAuthTestNavController()
        }

        composeTestRule.onSignUpNowLink()
            .assertIsDisplayed()
            .performClick()

        val newRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(newRoute).isEqualTo(signUpNavigationRoute)
    }

    @Test
    fun logInForm_whenClickingOnNoExistingAccountAuthError_shouldNavigateToSignUpScreen() {
        composeTestRule.setContent {
            navController = createAuthTestNavController()
        }

        composeTestRule.onNoExistingAccountErrorCard()
            .assertIsDisplayed()
            .performClick()

        val newRoute = navController.currentBackStackEntry?.destination?.route
        assertThat(newRoute).isEqualTo(signUpNavigationRoute)
    }

}

@Composable
private fun createAuthTestNavController(): TestNavHostController {
    val context = LocalContext.current
    return TestNavHostController(context).apply {
        this.navigatorProvider.addNavigator(ComposeNavigator())
        FakeAuthNavHost(navController = this)
    }
}

@Composable
private fun FakeAuthNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = logInNavigationRoute
    ) {
        composable(route = logInNavigationRoute) {
            LogInScreen(
                logInUiState = LogInUiState.AuthenticationFailed(emailAuthError = EmailError.Auth.NoExistingAccount),
                emailState = EmailState(onValidateEmail = { _ -> EmailError.Validation.EmptyField }),
                passwordState = PasswordState(onValidatePassword = { _ -> PasswordError.Validation.EmptyField }),
                onLogInSubmitted = { },
                onNavigateToSignUp = navController::navigateToSignUp
            )
        }
        signUpScreen()
    }
}
