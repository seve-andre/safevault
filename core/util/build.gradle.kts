plugins {
    id("safevault.android.library")
}

android {
    namespace = "com.mitch.safevault.core.util"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    api("androidx.annotation:annotation:1.7.0")
}
