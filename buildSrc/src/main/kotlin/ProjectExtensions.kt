import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project
import java.io.FileNotFoundException
import java.util.Properties

val Project.localProperties
    get() = try {
        Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }
    } catch (e: FileNotFoundException) {
        println(e)
        null
    }

/**
 * Implements all modules from [parent]
 *   - which are not top-level projects
 *   - it's parent isn't the [parent] itself
 *
 * :<anything> -> NO
 * :[parent]:<anything> -> NO
 *
 * :<NOT [parent]>:<anything> -> YES
 *
 * @sample addAllModules(projects.myApplication)
 */
fun DependencyHandlerScope.addAllModules(parent: DelegatingProjectDependency) {
    parent.dependencyProject.allprojects.filter {
        it.parent != null && it.parent != parent.dependencyProject
    }.map {
        ":${it.parent?.name}:${it.name}"
    }.forEach {
        add("implementation", project(it))
    }
}

/**
 * Implements all dependencies in [bundle].
 * Assumes that the first item in bundle is the BOM dependency.
 */
fun DependencyHandlerScope.implementationBundleBom(
    bundle: Provider<ExternalModuleDependencyBundle>
) {
    with(bundle.get()) {
        add("implementation", platform(first()))
        drop(1).forEach { add("implementation", it) }
    }
}
