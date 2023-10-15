package com.mitch.safevault.core.testing.util

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.getStringById(
    @StringRes id: Int,
    vararg formatArgs: Any
): String {
    return this.activity.resources.getString(id, formatArgs)
}