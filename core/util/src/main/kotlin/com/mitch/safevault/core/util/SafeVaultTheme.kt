package com.mitch.safevault.core.util

import androidx.annotation.StringRes

enum class SafeVaultTheme(
    @StringRes val translationId: Int
) {
    FollowSystem(R.string.system_default),
    Light(R.string.light_theme),
    Dark(R.string.dark_theme);

    companion object {
        fun default(): SafeVaultTheme {
            return FollowSystem
        }
    }
}
