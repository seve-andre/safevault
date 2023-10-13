package com.mitch.safevault.feature.onboarding.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mitch.safevault.feature.onboarding.R

/**
 * A sealed hierarchy describing a carousel item of a swipeable slideshow.
 *
 * @property imageId The resource ID of the image to be displayed.
 * @property titleId The resource ID of the title text.
 * @property bodyId The resource ID of the body text.
 */
sealed class CarouselItem(
    @DrawableRes val imageId: Int,
    @StringRes val titleId: Int,
    @StringRes val bodyId: Int
) {
    data object Shield : CarouselItem(
        imageId = R.drawable.shield,
        titleId = R.string.onboarding_shield_title,
        bodyId = R.string.onboarding_shield_body
    )

    data object Lock : CarouselItem(
        imageId = R.drawable.lock,
        titleId = R.string.onboarding_lock_title,
        bodyId = R.string.onboarding_lock_body
    )

    data object Key : CarouselItem(
        imageId = R.drawable.key,
        titleId = R.string.onboarding_key_title,
        bodyId = R.string.onboarding_key_body
    )
}
