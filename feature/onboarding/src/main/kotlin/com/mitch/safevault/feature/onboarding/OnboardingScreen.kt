package com.mitch.safevault.feature.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.feature.onboarding.component.CarouselControls
import com.mitch.safevault.feature.onboarding.component.CarouselItem
import com.mitch.safevault.feature.onboarding.component.CarouselPageIndicators
import com.mitch.safevault.feature.onboarding.component.SwipeableCarousel
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowForward
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
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

                FilledIconButton(
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    }
                ) {
                    Icon(
                        imageVector = EvaIcons.Outline.ArrowForward,
                        contentDescription = stringResource(id = R.string.go_to_next_page)
                    )
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
