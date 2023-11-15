package com.mitch.safevault.feature.auth.login

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.mitch.safevault.core.designsystem.theme.SafeVaultMaterialTheme
import com.mitch.safevault.core.ui.component.PasswordTextFieldState
import com.mitch.safevault.core.ui.component.TextFieldState
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.password.PasswordError
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
                    emailState = EmailState(
                        textFieldState = TextFieldState("fewfew"),
                        validationError = null
                    ),
                    passwordState = PasswordState(
                        textFieldState = PasswordTextFieldState("fewfewvve"),
                        validationError = null
                    ),
                    onStartEmailValidation = { },
                    onStartPasswordValidation = { },
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
                    emailState = EmailState(
                        textFieldState = TextFieldState(),
                        validationError = null
                    ),
                    passwordState = PasswordState(
                        textFieldState = PasswordTextFieldState(),
                        validationError = null
                    ),
                    onStartEmailValidation = { },
                    onStartPasswordValidation = { },
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
        composeTestRule.setContent {
            SafeVaultMaterialTheme {
                LogInScreen(
                    logInUiState = LogInUiState.Idle,
                    emailState = EmailState(
                        textFieldState = TextFieldState(""),
                        validationError = EmailError.Validation.EmptyField
                    ),
                    passwordState = PasswordState(
                        textFieldState = PasswordTextFieldState(),
                        validationError = PasswordError.Validation.EmptyField
                    ),
                    onStartEmailValidation = { },
                    onStartPasswordValidation = { },
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
                emailState = EmailState(
                    textFieldState = TextFieldState(),
                    validationError = EmailError.Validation.EmptyField
                ),
                passwordState = PasswordState(
                    textFieldState = PasswordTextFieldState(),
                    validationError = PasswordError.Validation.EmptyField
                ),
                onStartEmailValidation = { },
                onStartPasswordValidation = { },
                onLogInSubmitted = { },
                onNavigateToSignUp = navController::navigateToSignUp
            )
        }
        signUpScreen()
    }
}
