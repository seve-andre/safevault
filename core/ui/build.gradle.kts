plugins {
    alias(libs.plugins.safevault.android.library)
    alias(libs.plugins.safevault.android.library.compose)
}

android {
    namespace = "com.mitch.safevault.core.ui"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    api(libs.compose.foundation.layout)
    api(libs.compose.material3.windowSizeClass)
    debugApi(libs.compose.ui.tooling)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.ui.util)

    implementation(projects.core.designsystem)
    implementation(projects.core.util)
}
