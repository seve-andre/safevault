import com.mitch.safevault.util.libs
import com.mitch.safevault.util.testImplementation
import com.mitch.safevault.util.testRuntimeOnly
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class UnitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("de.mannodermaus.android-junit5")

            dependencies {
                testImplementation(libs.findLibrary("junit5").get())
                testRuntimeOnly(libs.findLibrary("junit5-engine").get())
                testImplementation(libs.findLibrary("junit5-params").get())
                testImplementation(libs.findLibrary("assertk").get())
            }
        }
    }
}
