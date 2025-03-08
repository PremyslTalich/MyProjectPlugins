import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyAndroidModule : Plugin<Project> by my plugin {
    project.pluginManager.apply("com.android.library")
    project.pluginManager.apply("org.jetbrains.kotlin.android")

    android {
        namespace = moduleNamespace(myVersions.namespace)
        compileSdk = myVersions.compileSdk.getInt()

        defaultConfig {
            minSdk = myVersions.minSdk.getInt()
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
    }
}