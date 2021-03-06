@file:Suppress("unused")

object Versions {
    const val kotlin = "1.4.0"
    const val android_gradle_plugin = "4.0.0"
    const val google_services = "4.3.4"
    const val firebase_crashlytics_gradle = "2.3.0"

    const val android_x_core = "1.1.0"
    const val support = "1.2.0"
    const val material = "1.3.0-alpha02"
    const val recyclerview = "1.0.0"
    const val constraint_layout = "2.0.0"
    const val viewpager2 = "1.0.0"
    const val lottie = "3.0.1"

    const val lifecycle = "2.2.0"
    const val navigation = "2.2.1"
    const val bottom_sheet_picker = "2.4.1"

    const val coroutines = "1.3.9"
    const val room = "2.2.5"

    const val koin = "2.1.6"
    const val work_manager = "2.4.0"
    const val timber = "4.7.1"
    const val firebase_bom = "25.12.0"
    const val firebase_crashlytics = "25.12.0"
    const val gson = "2.8.6"
}

object Deps {
    const val tools_android_gradle_plugin =
            "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val tools_kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val google_services = "com.google.gms:google-services:${Versions.google_services}"
    const val firebase_crashlytics_gradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebase_crashlytics_gradle}"
    const val androidx_core = "androidx.core:core-ktx:${Versions.android_x_core}"

    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val support_app_compat = "androidx.appcompat:appcompat:${Versions.support}"
    const val support_material = "com.google.android.material:material:${Versions.material}"
    const val support_recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val support_constraint_layout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    const val lifecycle_viewmodel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_viewmodel_savedstate =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val bottom_sheet_picker = "com.philliphsu:bottomsheetpickers:${Versions.bottom_sheet_picker}"

    const val coroutines_core =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    const val koin_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koin_viemmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    const val work_manager = "androidx.work:work-runtime-ktx:${Versions.work_manager}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
    const val firebase_crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebase_analytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebase_remote_config = "com.google.firebase:firebase-config-ktx"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}

object Repo {
    const val jitpack = "https://jitpack.io"
}