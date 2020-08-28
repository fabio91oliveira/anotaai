@file:Suppress("unused")

object Versions {
    const val kotlin = "1.3.70"
    const val android_gradle_plugin = "3.4.0"

    const val support = "1.2.0"
    const val material = "1.3.0-alpha02"
    const val recyclerview = "1.0.0"
    const val constraint_layout = "2.0.0"
    const val viewpager2 = "1.0.0"
    const val swipe_refresh_layout = "1.0.0"
    const val lottie = "3.0.1"

    const val lifecycle = "2.2.0"
    const val navigation = "2.2.1"

    const val coroutines = "1.3.2"
    const val resultcore = "2.2.0"
    const val resultcoroutines = "2.2.0"
    const val room = "2.2.2"
    const val gson = "2.8.6"

    const val koin = "2.1.3"
    const val timber = "4.7.1"
}

object Deps {
    const val tools_android_gradle_plugin =
            "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val tools_kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val support_app_compat = "androidx.appcompat:appcompat:${Versions.support}"
    const val support_material = "com.google.android.material:material:${Versions.material}"
    const val support_recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val support_constraint_layout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val swipe_refresh_layout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layout}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    const val lifecycle_viewmodel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_viewmodel_savedstate =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    const val coroutines_core =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val result_core = "com.github.kittinunf.result:result:${Versions.resultcore}"
    const val result_coroutines =
            "com.github.kittinunf.result:result-coroutines:${Versions.resultcoroutines}"

    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val koin_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koin_viemmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object Repo {
    const val jitpack = "https://jitpack.io"
}