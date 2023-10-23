plugins {
    alias(libs.plugins.safevault.android.library)
    alias(libs.plugins.safevault.android.hilt)
}

android {
    namespace = "com.mitch.safevault.core.data"
}

dependencies {
    api(libs.appcompat)
    api(libs.protobuf.kotlin.lite)

    implementation(projects.core.domain)
    implementation(projects.core.datastore)
    implementation(projects.core.util)
}
