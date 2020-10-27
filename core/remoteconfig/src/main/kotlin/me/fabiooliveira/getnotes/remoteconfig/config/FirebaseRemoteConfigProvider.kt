package me.fabiooliveira.getnotes.remoteconfig.config

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

private const val FETCH_INTERVAL_IN_SECONDS = 3600L

object FirebaseRemoteConfigProvider {
    fun providerRemoteConfig() = Firebase.remoteConfig.apply {
        val configs = remoteConfigSettings {
            minimumFetchIntervalInSeconds = FETCH_INTERVAL_IN_SECONDS
        }
        setConfigSettingsAsync(configs)
    }
}