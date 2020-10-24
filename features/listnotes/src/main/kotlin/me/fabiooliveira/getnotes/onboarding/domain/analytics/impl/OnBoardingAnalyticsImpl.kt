package me.fabiooliveira.getnotes.onboarding.domain.analytics.impl

import me.fabiooliveira.getnotes.analytics.domain.Analytics
import me.fabiooliveira.getnotes.onboarding.domain.analytics.OnBoardingAnalytics

private const val ON_BOARDING_SCREEN_VIEWED_EVENT = "on_boarding_screen_viewed"

internal class OnBoardingAnalyticsImpl(
        private val analytics: Analytics
) : OnBoardingAnalytics {
    override fun trackScreen() {
        analytics.trackEvent(ON_BOARDING_SCREEN_VIEWED_EVENT)
    }
}