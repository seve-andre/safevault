plugins {
    alias(libs.plugins.safevault.android.library)
    alias(libs.plugins.safevault.android.library.compose)
}

android {
    namespace = "com.mitch.safevault.core.designsystem"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    api(libs.compose.foundation.layout)
    api(libs.compose.material3)
    debugApi(libs.compose.ui.tooling)
    api(libs.compose.ui.tooling.preview)
    api(libs.accompanist.systemuicontroller)
    implementation(libs.icons.eva)
}
