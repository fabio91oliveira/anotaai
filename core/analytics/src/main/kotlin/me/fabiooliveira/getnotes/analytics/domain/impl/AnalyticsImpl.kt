package me.fabiooliveira.getnotes.analytics.domain.impl

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import me.fabiooliveira.getnotes.analytics.domain.Analytics

internal class AnalyticsImpl(
        private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {

    override fun trackEvent(event: String) {
        firebaseAnalytics.logEvent(event) {}
    }

    override fun trackEventWithParamsDefault(event: String, vararg item: String) {
        firebaseAnalytics.logEvent(event) {
            item.forEach {
                param(FirebaseAnalytics.Param.VALUE, it)
            }
        }
    }
}