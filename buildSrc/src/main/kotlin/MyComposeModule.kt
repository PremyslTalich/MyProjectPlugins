import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class MyComposeModule : Plugin<Project> by my plugin {
    apply<MyAndroidModule>()
    apply(plugin = myPlugins.compose.compiler.getPlugin())

    android {
        composeOptions {
            kotlinCompilerExtensionVersion = myVersions.kotlin.compiler.extension.get()
        }

        buildFeatures {
            compose = true
        }
    }

    dependencies {
        add("implementation", platform(myLibs.androidx.compose.bom))
        add("implementation", "androidx.compose.runtime:runtime")
    }
}