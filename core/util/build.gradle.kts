plugins {
    alias(libs.plugins.safevault.android.library)
    alias(libs.plugins.safevault.android.hilt)
}

android {
    namespace = "com.mitch.safevault.core.util"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidAnnotation)

    testImplementation(projects.core.testing)
}
