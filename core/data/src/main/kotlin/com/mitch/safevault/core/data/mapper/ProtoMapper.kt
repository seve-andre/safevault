package com.mitch.safevault.core.data.mapper

import com.mitch.safevault.core.datastore.ProtoUserPreferences.ProtoAppTheme
import com.mitch.safevault.core.util.SafeVaultTheme

fun SafeVaultTheme.toProtoModel(): ProtoAppTheme = when (this) {
    SafeVaultTheme.FOLLOW_SYSTEM -> ProtoAppTheme.FOLLOW_SYSTEM
    SafeVaultTheme.LIGHT -> ProtoAppTheme.LIGHT
    SafeVaultTheme.DARK -> ProtoAppTheme.DARK
}

fun ProtoAppTheme.toDomainModel(): SafeVaultTheme = when (this) {
    ProtoAppTheme.LIGHT -> SafeVaultTheme.LIGHT
    ProtoAppTheme.DARK -> SafeVaultTheme.DARK
    ProtoAppTheme.UNRECOGNIZED, ProtoAppTheme.FOLLOW_SYSTEM -> SafeVaultTheme.FOLLOW_SYSTEM
}
