import com.mitch.safevault.util.implementation
import com.mitch.safevault.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidNavigationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.findLibrary("navigation-compose").get())
            }
        }
    }
}
