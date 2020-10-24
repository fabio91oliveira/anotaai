package me.fabiooliveira.getnotes.analytics.config

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import core.analytics.BuildConfig

object FirebaseAnalyticsProvider {
    fun provideFirebaseAnalytics() = Firebase.analytics.apply {
        setAnalyticsCollectionEnabled(BuildConfig.IS_ANALYTICS_ENABLED)
    }
}