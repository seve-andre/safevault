package com.mitch.safevault.core.util

import androidx.annotation.DrawableRes
import java.util.Locale

enum class SafeVaultLanguage(
    val locale: Locale,
    @DrawableRes val flagId: Int
) {
    ENGLISH(locale = Locale.ENGLISH, flagId = R.drawable.english_flag),
    ITALIAN(locale = Locale.ITALIAN, flagId = R.drawable.italian_flag);

    companion object {
        fun fromLocale(locale: Locale): SafeVaultLanguage {
            return values().find { it.locale == locale } ?: default()
        }

        fun default(): SafeVaultLanguage {
            return ENGLISH
        }
    }
}
