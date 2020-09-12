package me.fabiooliveira.getnotes.di

import android.app.Application
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private const val DEFAULT = "default"

internal object AppModule {
    private val appModule = module {

        single {
            getSharedPrefs(androidApplication())
        }

        single<SharedPreferences.Editor> {
            getSharedPrefs(androidApplication()).edit()
        }
    }

    private fun getSharedPrefs(androidApplication: Application): SharedPreferences {
        return androidApplication.getSharedPreferences(DEFAULT, android.content.Context.MODE_PRIVATE)
    }

    fun load() = loadKoinModules(listOf(appModule))
}