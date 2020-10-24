package me.fabiooliveira.getnotes.analytics.domain

interface Analytics {
    fun trackEvent(event: String)
    fun trackEventWithParamsDefault(event: String, vararg item: String)
}