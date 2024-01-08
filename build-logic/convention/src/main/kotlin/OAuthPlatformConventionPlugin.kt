import com.sowhat.convention.configureOAuthPlatforms
import org.gradle.api.Plugin
import org.gradle.api.Project

class OAuthPlatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureOAuthPlatforms()
        }
    }
}