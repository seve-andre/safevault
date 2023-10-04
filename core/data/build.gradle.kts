plugins {
    id("safevault.android.library")
    id("safevault.android.hilt")
}

android {
    namespace = "com.mitch.safevault.core.data"
}

dependencies {
    api(libs.appcompat)
    api(libs.protobuf.kotlin.lite)

    implementation(project(":core:datastore"))
    implementation(project(":core:util"))
}
