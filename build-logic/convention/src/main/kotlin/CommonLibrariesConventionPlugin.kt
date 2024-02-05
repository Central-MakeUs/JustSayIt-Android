import com.sowhat.convention.configureCloudMessaging
import com.sowhat.convention.configureCommonLibraries
import com.sowhat.convention.configureOAuthPlatforms
import org.gradle.api.Plugin
import org.gradle.api.Project

class CommonLibrariesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureCommonLibraries()
            configureCloudMessaging()
        }
    }
}