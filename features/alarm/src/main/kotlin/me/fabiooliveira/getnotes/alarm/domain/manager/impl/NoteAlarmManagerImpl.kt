package me.fabiooliveira.getnotes.alarm.domain.manager.impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import me.fabiooliveira.getnotes.alarm.broadcastreceiver.NoteAlarmBroadcastReceiver
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_CONTENT
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_ID
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_TITLE
import me.fabiooliveira.getnotes.alarm.domain.manager.NoteAlarmManager
import org.koin.core.qualifier.named
import java.util.*

private const val FLAG = 0

internal class NoteAlarmManagerImpl(
        private val context: Context,
        private val alarmManager: AlarmManager,
        private val noteAlarmBroadcastReceiver: NoteAlarmBroadcastReceiver
) : NoteAlarmManager {
    override fun scheduleAlarm(noteId: Long,
                               noteTitle: String,
                               noteContent: String,
                               calendar: Calendar) {
        context.registerReceiver(noteAlarmBroadcastReceiver, IntentFilter(named<NoteAlarmBroadcastReceiver>().value))
        val intent = Intent(named<NoteAlarmBroadcastReceiver>().value)
        intent.putExtra(NOTE_ID, noteId)
        intent.putExtra(NOTE_TITLE, noteTitle)
        intent.putExtra(NOTE_CONTENT, noteContent)
//        myIntent.putExtra("uri", audioFileUri.toString()) // Here we pass the URI of audio file
        val pendingIntent = PendingIntent.getBroadcast(context, noteId.toInt(), intent, FLAG)
        calendar.set(Calendar.SECOND, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    override fun cancelAlarm(noteId: Long) {
        val intent = Intent(named<NoteAlarmBroadcastReceiver>().value)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, noteId.toInt(), intent, FLAG)
        alarmManager.cancel(alarmPendingIntent)
    }
}