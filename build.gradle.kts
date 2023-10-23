plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.library) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
