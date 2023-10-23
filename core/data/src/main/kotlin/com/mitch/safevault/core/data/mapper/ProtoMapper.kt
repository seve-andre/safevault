package com.mitch.safevault.core.data.mapper

import com.mitch.safevault.core.datastore.ProtoUserPreferences.ProtoAppTheme
import com.mitch.safevault.core.util.SafeVaultTheme

fun SafeVaultTheme.toDataModel(): ProtoAppTheme = when (this) {
    SafeVaultTheme.FollowSystem -> ProtoAppTheme.FOLLOW_SYSTEM
    SafeVaultTheme.Light -> ProtoAppTheme.LIGHT
    SafeVaultTheme.Dark -> ProtoAppTheme.DARK
}

fun ProtoAppTheme.toDomainModel(): SafeVaultTheme = when (this) {
    ProtoAppTheme.LIGHT -> SafeVaultTheme.Light
    ProtoAppTheme.DARK -> SafeVaultTheme.Dark
    ProtoAppTheme.UNRECOGNIZED, ProtoAppTheme.FOLLOW_SYSTEM -> SafeVaultTheme.FollowSystem
}
