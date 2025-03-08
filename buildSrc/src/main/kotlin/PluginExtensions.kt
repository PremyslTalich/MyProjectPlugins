import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the
import org.gradle.plugin.use.PluginDependency
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

internal object my
internal infix fun my.plugin(config: Project.() -> Unit) = Plugin<Project> { config(it) }


internal val Project.myPlugins: LibrariesForLibs.PluginAccessors
    get() = the<LibrariesForLibs>().plugins

internal val Project.myVersions
    get() = the<LibrariesForLibs>().versions.build

internal val Project.myLibs
    get() = the<LibrariesForLibs>()


internal fun Provider<PluginDependency>.getPlugin() = get().pluginId


internal fun Project.androidApp(configuration: BaseAppModuleExtension.() -> Unit) =
    extensions.getByType<BaseAppModuleExtension>().apply(configuration)

internal fun Project.android(configuration: LibraryExtension.() -> Unit) =
    extensions.getByType<LibraryExtension>().apply(configuration)

internal fun Project.java(configuration: JavaPluginExtension.() -> Unit) =
    extensions.getByType<JavaPluginExtension>().apply(configuration)

internal fun Project.kotlin(configuration: KotlinJvmProjectExtension.() -> Unit) =
    extensions.getByType<KotlinJvmProjectExtension>().apply(configuration)

internal fun LibraryExtension.kotlinOptions(configure: Action<KotlinJvmOptions>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlinOptions", configure)

internal fun BaseAppModuleExtension.kotlinOptions(configure: Action<KotlinJvmOptions>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlinOptions", configure)


/**
 * Returns module namespace derived from given app [namespace]
 *
 * with app namespace "com.example"
 *   - :library:cache     -> "com.example.library.cache"
 *   - :library:long-name -> "com.example.library.long.name"
 */
fun Project.moduleNamespace(namespace: Provider<String>) =
    "${namespace.get()}.${parent?.name}.${name}".replace("-", "")

/**
 * Takes provided version name and turns it into version code
 *
 * "0.1" -> 1
 * "1.0" -> 10
 * "2.3" -> 23
 */
val Provider<String>.code
    get() = get().replace(".", "").toInt()

/**
 * Return [Provider<String>] as [Int].
 *
 * e.g. libs.versions.build.compileSdk.getInt()
 */
fun Provider<String>.getInt() = get().toInt()
