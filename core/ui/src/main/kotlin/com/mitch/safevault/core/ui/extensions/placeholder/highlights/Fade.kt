package com.mitch.safevault.core.ui.extensions.placeholder.highlights

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import com.mitch.safevault.core.ui.extensions.placeholder.PlaceholderDefaults
import com.mitch.safevault.core.ui.extensions.placeholder.PlaceholderHighlight

private data class Fade(
    private val highlightColor: Color,
    override val animationSpec: InfiniteRepeatableSpec<Float>,
) : PlaceholderHighlight {
    private val brush = SolidColor(highlightColor)

    override fun brush(progress: Float, size: Size): Brush = brush
    override fun alpha(progress: Float): Float = progress
}

@Composable
fun PlaceholderHighlight.Companion.fade(
    animationSpec: InfiniteRepeatableSpec<Float> = com.mitch.safevault.core.ui.extensions.placeholder.PlaceholderDefaults.fadeAnimationSpec,
): PlaceholderHighlight = Fade(
    highlightColor = com.mitch.safevault.core.ui.extensions.placeholder.PlaceholderDefaults.fadeHighlightColor(),
    animationSpec = animationSpec,
)

@Composable
fun PlaceholderDefaults.fadeHighlightColor(
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    alpha: Float = 0.3f,
): Color = backgroundColor.copy(alpha = alpha)
