plugins {
    alias(libs.plugins.safevault.android.library)
    alias(libs.plugins.safevault.android.hilt)
}

android {
    namespace = "com.mitch.safevault.core.domain"
}

dependencies {
    implementation(projects.core.util)
    implementation(projects.core.model)
    testImplementation(projects.core.testing)
}
