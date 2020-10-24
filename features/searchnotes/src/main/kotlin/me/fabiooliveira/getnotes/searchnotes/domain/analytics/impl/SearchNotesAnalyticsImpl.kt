package me.fabiooliveira.getnotes.searchnotes.domain.analytics.impl

import me.fabiooliveira.getnotes.analytics.domain.Analytics
import me.fabiooliveira.getnotes.searchnotes.domain.analytics.SearchNotesAnalytics

private const val SEARCH_NOTES_SCREEN_VIEWED_EVENT = "search_notes_screen_viewed"

internal class SearchNotesAnalyticsImpl(
        private val analytics: Analytics
) : SearchNotesAnalytics {
    override fun trackScreen() {
        analytics.trackEvent(SEARCH_NOTES_SCREEN_VIEWED_EVENT)
    }
}