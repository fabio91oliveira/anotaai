package me.fabiooliveira.getnotes.alarm.domain.manager

internal interface ReminderStatusManager {
    fun cancelReminder(noteId: Long)
}