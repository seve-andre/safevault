package com.mitch.safevault.core.util

import androidx.annotation.DrawableRes
import java.util.Locale

enum class SafeVaultLanguage(
    val locale: Locale,
    @DrawableRes val flagId: Int
) {
    English(locale = Locale.ENGLISH, flagId = R.drawable.english_flag),
    Italian(locale = Locale.ITALIAN, flagId = R.drawable.italian_flag);

    companion object {
        fun fromLocale(locale: Locale): SafeVaultLanguage {
            return values().find { it.locale == locale } ?: default()
        }

        private fun default(): SafeVaultLanguage {
            return English
        }
    }
}
