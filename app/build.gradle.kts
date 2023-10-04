plugins {
    id("safevault.android.application")
    id("safevault.android.application.compose")
    id("safevault.android.hilt")
    id("safevault.android.navigation")
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

    // Formatting + Linting
    lintChecks(libs.linting.composeLints)

    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))

    implementation(project(":feature:home"))
}
