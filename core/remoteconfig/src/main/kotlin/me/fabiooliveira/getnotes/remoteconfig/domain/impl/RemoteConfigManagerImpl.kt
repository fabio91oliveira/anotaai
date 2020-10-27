package me.fabiooliveira.getnotes.remoteconfig.domain.impl

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import me.fabiooliveira.getnotes.remoteconfig.domain.RemoteConfigManager
import timber.log.Timber

internal class RemoteConfigManagerImpl(
        private val remoteConfig: FirebaseRemoteConfig,
        private val gson: Gson
) : RemoteConfigManager {
    override fun fetchAndActive() {
        remoteConfig.fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val updated = task.result
                        Timber.i("Config params updated: $updated")
                    } else {
                        Timber.i("Config params failed")
                    }
                }
    }

    override fun <T> getDynamicObject(key: String, clazz: Class<T>): T? {
        return try {
            val json = remoteConfig.getString(key)
            return gson.fromJson(json, clazz)
        } catch (ex: JsonSyntaxException) {
            Timber.e(ex)
            null
        }
    }
}