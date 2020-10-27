package me.fabiooliveira.getnotes.analytics.domain.impl

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import core.analytics.BuildConfig
import me.fabiooliveira.getnotes.analytics.domain.Analytics

internal class AnalyticsImpl(
        private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {

    override fun trackEvent(event: String) {
        if (isAnalyticsEnabled()) {
            firebaseAnalytics.logEvent(event) {}
        }
    }

    override fun trackEventWithParamsDefault(event: String, vararg item: String) {
        if (isAnalyticsEnabled()) {
            firebaseAnalytics.logEvent(event) {
                item.forEach {
                    param(FirebaseAnalytics.Param.VALUE, it)
                }
            }
        }
    }

    private fun isAnalyticsEnabled() = BuildConfig.IS_ANALYTICS_ENABLED
}