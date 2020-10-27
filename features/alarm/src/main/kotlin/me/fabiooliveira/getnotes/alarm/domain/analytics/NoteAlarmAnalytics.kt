package me.fabiooliveira.getnotes.alarm.domain.analytics

internal interface NoteAlarmAnalytics {
    fun trackAlarmScheduled()
    fun trackAlarmCancelled()
    fun trackAlarmStarted()
    fun trackAlarmStopped()
}