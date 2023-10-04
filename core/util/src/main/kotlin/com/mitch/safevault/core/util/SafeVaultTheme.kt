package com.mitch.safevault.core.util

import androidx.annotation.StringRes

enum class SafeVaultTheme(
    @StringRes val translationId: Int
) {
    FOLLOW_SYSTEM(R.string.system_default),
    LIGHT(R.string.light_theme),
    DARK(R.string.dark_theme);

    companion object {
        fun default(): SafeVaultTheme {
            return FOLLOW_SYSTEM
        }
    }
}
