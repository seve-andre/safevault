package com.mitch.safevault.feature.onboarding

import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.mitch.safevault.core.designsystem.theme.LightColorScheme
import com.mitch.safevault.core.designsystem.theme.SafeVaultMaterialTheme
import com.mitch.safevault.core.testing.util.assertBackgroundColor
import com.mitch.safevault.core.testing.util.getStringById
import com.mitch.safevault.feature.onboarding.component.CarouselItem
import com.mitch.safevault.feature.onboarding.component.pageIndicatorTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.mitch.safevault.core.util.R as utilR

class OnboardingScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val skipButton by lazy {
        composeTestRule.onNodeWithText(composeTestRule.getStringById(R.string.skip))
    }

    private val nextButton by lazy {
        composeTestRule.onNodeWithContentDescription(composeTestRule.getStringById(R.string.go_to_next_page))
    }

    private val logInButton by lazy {
        composeTestRule.onNodeWithText(composeTestRule.getStringById(utilR.string.log_in))
    }

    private val signUpButton by lazy {
        composeTestRule.onNodeWithText(composeTestRule.getStringById(utilR.string.sign_up))
    }

    private val firstCarouselItem by lazy {
        listOf(
            composeTestRule.onNodeWithTag(CarouselItem.Shield.imageId.toString()),
            composeTestRule.onNodeWithText(composeTestRule.getStringById(CarouselItem.Shield.titleId)),
            composeTestRule.onNodeWithText(composeTestRule.getStringById(CarouselItem.Shield.bodyId))
        )
    }

    private val secondCarouselItem by lazy {
        listOf(
            composeTestRule.onNodeWithTag(CarouselItem.Lock.imageId.toString()),
            composeTestRule.onNodeWithText(composeTestRule.getStringById(CarouselItem.Lock.titleId)),
            composeTestRule.onNodeWithText(composeTestRule.getStringById(CarouselItem.Lock.bodyId))
        )
    }

    private val thirdCarouselItem by lazy {
        listOf(
            composeTestRule.onNodeWithTag(CarouselItem.Key.imageId.toString()),
            composeTestRule.onNodeWithText(composeTestRule.getStringById(CarouselItem.Key.titleId)),
            composeTestRule.onNodeWithText(composeTestRule.getStringById(CarouselItem.Key.bodyId))
        )
    }

    private fun indicator(number: Int): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithTag(pageIndicatorTag(number))
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SafeVaultMaterialTheme(isThemeDark = false) {
                OnboardingRoute(onNavigateToSignUp = { }, onNavigateToLogIn = { })
            }
        }
    }

    @Test
    fun carouselItems_whenClickingNext_hasCorrectOrder() {
        firstCarouselItem.forEach { it.assertIsDisplayed() }

        nextButton.assertIsDisplayed().performClick()

        secondCarouselItem.forEach { it.assertIsDisplayed() }

        nextButton.assertIsDisplayed().performClick()

        thirdCarouselItem.forEach { it.assertIsDisplayed() }
    }

    @Test
    fun carouselItems_whenSwiping_hasCorrectOrder() {
        firstCarouselItem.forEach { it.assertIsDisplayed() }

        composeTestRule.onRoot().performTouchInput { swipeLeft() }

        secondCarouselItem.forEach { it.assertIsDisplayed() }

        composeTestRule.onRoot().performTouchInput { swipeLeft() }

        thirdCarouselItem.forEach { it.assertIsDisplayed() }
    }

    @Test
    fun carouselItems_whenSkipping_lastItemIsDisplayed() {
        skipButton.assertIsDisplayed().performClick()

        thirdCarouselItem.forEach { it.assertIsDisplayed() }
    }

    @Test
    fun carouselItems_eachHasCorrectControls() {
        skipButton.assertIsDisplayed()
        nextButton.assertIsDisplayed()

        composeTestRule.onRoot().performTouchInput { swipeLeft() }

        skipButton.assertIsDisplayed()
        nextButton.assertIsDisplayed()

        composeTestRule.onRoot().performTouchInput { swipeLeft() }

        logInButton.assertIsDisplayed()
        signUpButton.assertIsDisplayed()
    }

    @Test
    fun carouselItems_eachHasCorrectIndicator() {
        indicator(0)
            .assertIsDisplayed()
            .assertBackgroundColor(LightColorScheme.primary)

        indicator(1)
            .assertIsDisplayed()
            .assertBackgroundColor(Color.Transparent)

        indicator(2)
            .assertIsDisplayed()
            .assertBackgroundColor(Color.Transparent)

        composeTestRule.onRoot().performTouchInput { swipeLeft() }

        indicator(0)
            .assertIsDisplayed()
            .assertBackgroundColor(Color.Transparent)

        indicator(1)
            .assertIsDisplayed()
            .assertBackgroundColor(LightColorScheme.primary)

        indicator(2)
            .assertIsDisplayed()
            .assertBackgroundColor(Color.Transparent)

        composeTestRule.onRoot().performTouchInput { swipeLeft() }

        indicator(0)
            .assertIsDisplayed()
            .assertBackgroundColor(Color.Transparent)

        indicator(1)
            .assertIsDisplayed()
            .assertBackgroundColor(Color.Transparent)

        indicator(2)
            .assertIsDisplayed()
            .assertBackgroundColor(LightColorScheme.primary)
    }
}
