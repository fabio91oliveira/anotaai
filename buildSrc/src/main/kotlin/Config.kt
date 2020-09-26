@file:Suppress("unused")

import org.gradle.api.JavaVersion

object Config {
    val javaVersion = JavaVersion.VERSION_1_8

    const val minSdk = 23
    const val compileSdk = 28
    const val targetSdk = 28
    const val versionCode = 7
    const val versionName = "2.1.0"
    const val buildTools = "28.0.3"
    const val applicationId = "me.fabiooliveira.getnotes"

    const val srcDirMain = "src/main/kotlin"
    const val srcDirTest = "src/test/kotlin"
    const val srcDirAndroidTest = "src/androidTest/kotlin"

    const val runner = "androidx.test.runner.AndroidJUnitRunner"
    const val orchestrator = "ANDROIDX_TEST_ORCHESTRATOR"
}