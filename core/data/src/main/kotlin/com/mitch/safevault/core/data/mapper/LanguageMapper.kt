package com.mitch.safevault.core.data.mapper

import com.mitch.safevault.core.util.SafeVaultLanguage
import java.util.Locale

fun Locale.toAppLanguage(): SafeVaultLanguage {
    // removes country code and variants if present
    val localeLanguageOnly = Locale.forLanguageTag(this.language)

    return SafeVaultLanguage.fromLocale(localeLanguageOnly)
}
