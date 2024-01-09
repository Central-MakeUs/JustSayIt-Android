import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.sowhat.convention.configureComposeAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureComposeAndroid()
        }
    }
}