@file:Suppress("unused")

import org.gradle.api.JavaVersion

object Config {
    val javaVersion = JavaVersion.VERSION_1_8

    const val minSdk = 23
    const val compileSdk = 29
    const val targetSdk = 29
    const val versionCode = 9
    const val versionName = "3.1.0"
    const val buildTools = "29.0.2"
    const val applicationId = "me.fabiooliveira.getnotes"

    const val srcDirMain = "src/main/kotlin"
    const val srcDirTest = "src/test/kotlin"
    const val srcDirAndroidTest = "src/androidTest/kotlin"

    const val runner = "androidx.test.runner.AndroidJUnitRunner"
    const val orchestrator = "ANDROIDX_TEST_ORCHESTRATOR"
}