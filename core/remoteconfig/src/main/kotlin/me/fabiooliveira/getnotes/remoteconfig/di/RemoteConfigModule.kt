package me.fabiooliveira.getnotes.remoteconfig.di

import com.google.gson.GsonBuilder
import me.fabiooliveira.getnotes.remoteconfig.config.FirebaseRemoteConfigProvider
import me.fabiooliveira.getnotes.remoteconfig.domain.RemoteConfigManager
import me.fabiooliveira.getnotes.remoteconfig.domain.impl.RemoteConfigManagerImpl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object RemoteConfigModule {
    private val remoteConfigModule = module {
        factory { GsonBuilder().create() }
        single<RemoteConfigManager> {
            RemoteConfigManagerImpl(
                    remoteConfig = FirebaseRemoteConfigProvider.providerRemoteConfig(),
                    gson = get()
            )
        }
    }

    fun load() = loadKoinModules(listOf(remoteConfigModule))
}