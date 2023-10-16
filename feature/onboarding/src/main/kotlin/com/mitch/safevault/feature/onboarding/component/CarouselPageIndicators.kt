package com.mitch.safevault.feature.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.theme.padding

internal fun pageIndicatorTag(indicator: Int): String {
    return "page_indicator_$indicator"
}

@Composable
internal fun CarouselPageIndicators(
    pageCountProvider: () -> Int,
    currentPageProvider: () -> Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = padding.small,
            alignment = Alignment.CenterHorizontally
        )
    ) {
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
                    .testTag(pageIndicatorTag(it))
            )
        }
    }
}
