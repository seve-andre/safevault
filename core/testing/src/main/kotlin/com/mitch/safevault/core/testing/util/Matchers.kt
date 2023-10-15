package com.mitch.safevault.core.testing.util

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.TestCase.assertEquals

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.getStringById(
    @StringRes id: Int,
    vararg formatArgs: Any
): String {
    return this.activity.resources.getString(id, formatArgs)
}

fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color) {
    val capturedName = this.captureToImage().colorSpace.name
    assertEquals(expectedBackground.colorSpace.name, capturedName)
}