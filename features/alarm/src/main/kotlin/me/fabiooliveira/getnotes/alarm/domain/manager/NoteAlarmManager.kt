package me.fabiooliveira.getnotes.alarm.domain.manager

import java.util.*

const val NOTE_ID = "NOTE_ID"
const val NOTE_TITLE = "NOTE_TITLE"
const val NOTE_CONTENT = "NOTE_CONTENT"
const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
const val CHANNEL_NAME = "Alarm Service Channel"

interface NoteAlarmManager {
    fun scheduleAlarm(noteId: Long,
                      noteTitle: String,
                      noteContent: String,
                      calendar: Calendar)

    fun cancelAlarm(noteId: Long)
}