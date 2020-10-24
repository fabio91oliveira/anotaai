package me.fabiooliveira.getnotes.analytics.di

import me.fabiooliveira.getnotes.analytics.config.FirebaseAnalyticsProvider
import me.fabiooliveira.getnotes.analytics.domain.Analytics
import me.fabiooliveira.getnotes.analytics.domain.impl.AnalyticsImpl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AnalyticsModule {
    private val analyticsModule = module {
        single<Analytics> {
            AnalyticsImpl(
                    firebaseAnalytics = FirebaseAnalyticsProvider.provideFirebaseAnalytics()
            )
        }
    }

    fun load() = loadKoinModules(listOf(analyticsModule))
}