## Some steps

1. Update version catalog :point_right: [libs.versions.toml](https://github.com/PremyslTalich/MyProjectPlugins/blob/master/gradle/libs.versions.toml)

3. Update project `settings.gradle.kts`
   - Keep `rootProject.name = "MyApplication"` without whitespaces
   - Add 👇 to enable type-safe access to subprojects, e.g.: `implementation(projects.library.cache)`
     ```
     enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
     ```
4. Update project `build.gradle.kts`
   - Add
     ```
     alias(libs.plugins.compose.compiler) apply false
     ```
   - Remove
     ```
     alias(libs.plugins.android.application) apply false
     alias(libs.plugins.kotlin.android) apply false
     alias(libs.plugins.jetbrains.kotlin.jvm) apply false
     alias(libs.plugins.android.library) apply false
     ```
5. Apply buildSrc patch
   ```
   Invoke-WebRequest -Uri "https://github.com/PremyslTalich/MyProjectPlugins/commit/36483fe2976ed609d0e75c963bc13f724fe50c5b.patch" -OutFile "commit.patch"; git apply commit.patch; git add buildSrc; Remove-Item commit.patch
   ```
6. Update modules `build.gradle.kts` to use the `my.<type>-module` plugins
   ```kotlin
   id("my.app-module")
   ```
   ```kotlin
   id("my.android-module")
   ```
   ```kotlin
   id("my.compose-module")
   ```
   ```kotlin
   id("my.kotlin-module")
   ```


## Project extensions
```kotlin
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
fun DependencyHandlerScope.addAllModules(parent: DelegatingProjectDependency)
```
```kotlin
/**
 * Implements all dependencies in [bundle].
 * Assumes that the first item in bundle is the BOM dependency.
 */
fun DependencyHandlerScope.implementationBundleBom(bundle: Provider<ExternalModuleDependencyBundle>)
```