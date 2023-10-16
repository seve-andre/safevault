package com.mitch.safevault.feature.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.feature.onboarding.component.CarouselControls
import com.mitch.safevault.feature.onboarding.component.CarouselItem
import com.mitch.safevault.feature.onboarding.component.CarouselPageIndicators
import com.mitch.safevault.feature.onboarding.component.SwipeableCarousel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun OnboardingRoute(
    onNavigateToSignup: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val carouselItems = persistentListOf(
        CarouselItem.Shield,
        CarouselItem.Lock,
        CarouselItem.Key
    )
    val pagerState = rememberPagerState(pageCount = { carouselItems.size })
    val scope = rememberCoroutineScope()
    val isNotLastItem by remember {
        derivedStateOf { pagerState.currentPage + 1 != carouselItems.size }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SwipeableCarousel(
            carouselState = pagerState,
            items = carouselItems,
            modifier = Modifier.weight(6f)
        )
        CarouselPageIndicators(
            pageCountProvider = { pagerState.pageCount },
            currentPageProvider = { pagerState.currentPage },
            modifier = Modifier.weight(1f)
        )

        CarouselControls(
            modifier = Modifier.weight(1f)
        ) {
            if (isNotLastItem) {
                TextButton(
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(carouselItems.lastIndex)
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.skip))
                }

                TooltipBox(
                    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                    tooltip = {
                        PlainTooltip {
                            Text(text = stringResource(id = R.string.go_to_next_page))
                        }
                    },
                    state = rememberTooltipState()
                ) {
                    FilledIconButton(
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = SafeVaultIcons.ArrowRight,
                            contentDescription = stringResource(id = R.string.go_to_next_page)
                        )
                    }
                }
            } else {
                OutlinedButton(onClick = onNavigateToLogin) {
                    Text(text = stringResource(id = R.string.log_in))
                }

                Button(onClick = onNavigateToSignup) {
                    Text(text = stringResource(id = R.string.sign_up))
                }
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingPreview() {
    OnboardingRoute(
        onNavigateToSignup = { },
        onNavigateToLogin = { }
    )
}
