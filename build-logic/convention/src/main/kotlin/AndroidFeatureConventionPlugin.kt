import com.android.build.gradle.LibraryExtension
import com.mitch.safevault.util.androidTestImplementation
import com.mitch.safevault.util.implementation
import com.mitch.safevault.util.libs
import com.mitch.safevault.util.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("safevault.android.library")
                apply("safevault.android.hilt")
                apply("safevault.android.navigation")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
//                    testInstrumentationRunner = "com.mitch.safevault.core.testing.NiaTestRunner"
                }
            }

            dependencies {
                implementation(project(":core:data"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:domain"))
                implementation(project(":core:model"))
                implementation(project(":core:ui"))
                implementation(project(":core:util"))

                implementation(libs.findLibrary("lifecycle-runtimeCompose").get())
                implementation(libs.findLibrary("lifecycle-viewModel-compose").get())

                implementation(libs.findLibrary("kotlinx-coroutines-android").get())
            }
        }
    }
}
