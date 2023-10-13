plugins {
    alias(libs.plugins.safevault.android.library)
    alias(libs.plugins.safevault.android.library.compose)
    alias(libs.plugins.safevault.android.hilt)
}

android {
    namespace = "com.mitch.safevault.core.testing"
}

dependencies {
    api(libs.accompanist.testharness)
    api(libs.activity.compose)
    api(libs.composeTest.junit4)
    api(libs.test.core)
    api(libs.test.espresso.core)
    api(libs.test.rules)
    api(libs.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit4)
    api(libs.junit5)
    api(libs.junit5.engine)
    api(libs.junit5.params)
    api(libs.assertk)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)

    debugApi(libs.composeTest.manifest)

    implementation(projects.core.util)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.model)
}
