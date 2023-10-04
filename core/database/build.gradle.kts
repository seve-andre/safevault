plugins {
    id("safevault.android.library")
    id("safevault.android.hilt")
    id("safevault.android.room")
}

android {
    namespace = "com.mitch.safevault.core.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

}
