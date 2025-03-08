plugins {
    `kotlin-dsl`
    kotlin("jvm") version libs.versions.kotlin
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.android.gradle.plugin) // com.android.tools.build:gradle
    implementation(libs.kotlin.gradle.plugin) // org.jetbrains.kotlin:kotlin-gradle-plugin

    // workaround for enabling version catalog inside plugins - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("my.app-module") {
            id = "my.app-module"
            implementationClass = "MyAppModule"
        }
        register("my.android-module") {
            id = "my.android-module"
            implementationClass = "MyAndroidModule"
        }
        register("my.compose-module") {
            id = "my.compose-module"
            implementationClass = "MyComposeModule"
        }
        register("my.kotlin-module") {
            id = "my.kotlin-module"
            implementationClass = "MyKotlinModule"
        }
    }
}
