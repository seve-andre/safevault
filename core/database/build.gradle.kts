plugins {
    alias(libs.plugins.safevault.android.library)
    alias(libs.plugins.safevault.android.hilt)
    alias(libs.plugins.safevault.android.room)
}

android {
    namespace = "com.mitch.safevault.core.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}
