import com.mitch.safevault.util.implementation
import com.mitch.safevault.util.ksp
import com.mitch.safevault.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            dependencies {
                implementation(libs.findLibrary("room-runtime").get())
                implementation(libs.findLibrary("room-ktx").get())
                ksp(libs.findLibrary("room-compiler").get())
            }
        }
    }
}
