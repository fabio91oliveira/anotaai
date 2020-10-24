package me.fabiooliveira.getnotes.alarm.domain.analytics.impl

import me.fabiooliveira.getnotes.alarm.domain.analytics.NoteAlarmAnalytics
import me.fabiooliveira.getnotes.analytics.domain.Analytics

private const val NOTE_ALARM_SCHEDULED_EVENT = "note_alarm_scheduled"
private const val NOTE_ALARM_CANCELLED_EVENT = "note_alarm_cancelled"
private const val NOTE_ALARM_STARTED_EVENT = "note_alarm_started"
private const val NOTE_ALARM_STOPPED_EVENT = "note_alarm_stopped"

internal class NoteAlarmAnalyticsImpl(
        private val analytics: Analytics
) : NoteAlarmAnalytics {
    override fun trackAlarmScheduled() {
        analytics.trackEvent(NOTE_ALARM_SCHEDULED_EVENT)
    }

    override fun trackAlarmCancelled() {
        analytics.trackEvent(NOTE_ALARM_CANCELLED_EVENT)
    }

    override fun trackAlarmStarted() {
        analytics.trackEvent(NOTE_ALARM_STARTED_EVENT)
    }

    override fun trackAlarmStopped() {
        analytics.trackEvent(NOTE_ALARM_STOPPED_EVENT)
    }
}