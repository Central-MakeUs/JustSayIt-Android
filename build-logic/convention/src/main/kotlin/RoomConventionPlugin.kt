import com.sowhat.convention.configureRoom
import org.gradle.api.Plugin
import org.gradle.api.Project

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureRoom()
        }
    }
}