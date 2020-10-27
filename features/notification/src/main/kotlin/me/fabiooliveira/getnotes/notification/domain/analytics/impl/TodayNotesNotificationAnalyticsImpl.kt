package me.fabiooliveira.getnotes.notification.domain.analytics.impl

import me.fabiooliveira.getnotes.analytics.domain.Analytics
import me.fabiooliveira.getnotes.notification.domain.analytics.TodayNotesNotificationAnalytics

private const val TODAY_NOTES_NOTIFICATION_NOTIFIED_EVENT = "today_notes_notification_notified"

internal class TodayNotesNotificationAnalyticsImpl(
        private val analytics: Analytics
) : TodayNotesNotificationAnalytics {
    override fun trackNotified() {
        analytics.trackEvent(TODAY_NOTES_NOTIFICATION_NOTIFIED_EVENT)
    }
}