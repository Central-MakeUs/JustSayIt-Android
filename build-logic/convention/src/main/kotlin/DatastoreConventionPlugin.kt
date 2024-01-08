import com.sowhat.convention.configureDatastore
import org.gradle.api.Plugin
import org.gradle.api.Project

class DatastoreConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureDatastore()
        }
    }
}