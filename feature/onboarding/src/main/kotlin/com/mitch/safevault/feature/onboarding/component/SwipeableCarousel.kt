package com.mitch.safevault.feature.onboarding.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.theme.padding
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SwipeableCarousel(
    carouselState: PagerState,
    items: ImmutableList<CarouselItem>,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = carouselState,
        modifier = modifier
    ) { index ->
        val currentItem = items[index]
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = currentItem.imageId),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(padding.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = currentItem.titleId),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                )
                Text(
                    text = stringResource(id = currentItem.bodyId),
                    modifier = Modifier.padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )
            }

            CarouselDotIndicators(
                pageCountProvider = { carouselState.pageCount },
                currentPageProvider = { carouselState.currentPage }
            )
        }
    }
}

@Composable
private fun CarouselDotIndicators(
    pageCountProvider: () -> Int,
    currentPageProvider: () -> Int
) {
    Row(horizontalArrangement = Arrangement.spacedBy(padding.small)) {
        repeat(pageCountProvider()) {
            val backgroundColor =
                if (currentPageProvider() == it) MaterialTheme.colorScheme.primary else Color.Transparent
            val borderColor =
                if (currentPageProvider() == it) Color.Transparent else MaterialTheme.colorScheme.onBackground

            Box(
                modifier = Modifier
                    .size(15.dp)
                    .background(
                        color = backgroundColor,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = CircleShape
                    )
            )
        }
    }
}
