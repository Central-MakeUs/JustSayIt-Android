import com.sowhat.convention.configureHiltAndroid
import com.sowhat.convention.configureOAuthPlatforms
import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureHiltAndroid()
        }
    }
}