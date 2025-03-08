import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class MyAppModule : Plugin<Project> by my plugin {
    apply(plugin = "com.android.application")
    apply(plugin = "org.jetbrains.kotlin.android")
    apply(plugin = myPlugins.compose.compiler.getPlugin())

    dependencies {
        add("implementation", platform(myLibs.androidx.compose.bom))
        add("implementation", "androidx.compose.runtime:runtime")
    }

    androidApp {
        namespace = myVersions.namespace.get()
        compileSdk = myVersions.compileSdk.getInt()

        defaultConfig {
            applicationId = myVersions.app.id.get()
            minSdk = myVersions.minSdk.getInt()
            targetSdk = myVersions.compileSdk.getInt()
            versionCode = myVersions.app.version.code
            versionName = myVersions.app.version.get()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            debug {
                isMinifyEnabled = false
                enableUnitTestCoverage = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            release {
                isMinifyEnabled = true
                enableUnitTestCoverage = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        sourceSets {
            getByName("main") {
                kotlin.srcDirs("src/main/kotlin")
            }
            getByName("debug") {
                kotlin.srcDirs("src/debug/kotlin")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.toVersion(myVersions.java.get())
            targetCompatibility = JavaVersion.toVersion(myVersions.java.get())
        }

        kotlinOptions {
            jvmTarget = myVersions.java.get()
        }

        composeOptions {
            kotlinCompilerExtensionVersion = myVersions.kotlin.compiler.extension.get()
        }

        buildFeatures {
            compose = true
        }
    }
}