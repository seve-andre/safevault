plugins {
    alias(libs.plugins.safevault.android.application)
    alias(libs.plugins.safevault.android.application.compose)
    alias(libs.plugins.safevault.android.hilt)
}

val packageName = "com.mitch.safevault"

android {
    namespace = packageName

    defaultConfig {
        applicationId = packageName
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        androidResources {
            generateLocaleConfig = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.splashscreen)
    implementation(libs.lifecycle.runtimeCompose)

    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.util)

    implementation(projects.feature.items)
}
