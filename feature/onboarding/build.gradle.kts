plugins {
    alias(libs.plugins.safevault.android.feature)
    alias(libs.plugins.safevault.android.library.compose)
}

android {
    namespace = "com.mitch.safevault.feature.onboarding"
}

dependencies {
    api(libs.kotlinx.immutableCollections)
    api(libs.icons.eva)
}
