package com.mitch.safevault.feature.auth.login

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mitch.safevault.core.testing.util.getStringById
import com.mitch.safevault.feature.auth.R

typealias SafeVaultComposeTestRule<A> = AndroidComposeTestRule<ActivityScenarioRule<A>, A>

fun <A : ComponentActivity> SafeVaultComposeTestRule<A>.onNoExistingAccountErrorCard(): SemanticsNodeInteraction {
    return this.onNode(
        hasText(
            text = this.getStringById(R.string.no_existing_account),
            substring = true
        )
    )
}

fun <A : ComponentActivity> SafeVaultComposeTestRule<A>.onSignUpNowLink(): SemanticsNodeInteraction {
    return this.onNode(
        hasText(
            text = this.getStringById(R.string.no_account_question),
            substring = true
        )
    )
}

fun <A : ComponentActivity> SafeVaultComposeTestRule<A>.onError(
    @StringRes errorId: Int
): SemanticsNodeInteraction {
    return this.onNodeWithText(this.getStringById(errorId))
}

internal fun <A : ComponentActivity> logInScreenRobot(
    composeRule: SafeVaultComposeTestRule<A>,
    func: LogInScreenRobot<A>.() -> Unit
) = LogInScreenRobot(composeRule).also(func)

class LogInScreenRobot<A : ComponentActivity>(
    private val composeRule: SafeVaultComposeTestRule<A>
)
