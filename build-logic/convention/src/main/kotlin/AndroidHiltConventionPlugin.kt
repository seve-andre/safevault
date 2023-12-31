import com.mitch.safevault.util.implementation
import com.mitch.safevault.util.ksp
import com.mitch.safevault.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                // KAPT must go last to avoid build warnings.
                // See: https://stackoverflow.com/questions/70550883/warning-the-following-options-were-not-recognized-by-any-processor-dagger-f
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(libs.findLibrary("hilt-android").get())
                ksp(libs.findLibrary("hilt-android-compiler").get())
                implementation(libs.findLibrary("hilt-navigation-compose").get())
            }

        }
    }
}
