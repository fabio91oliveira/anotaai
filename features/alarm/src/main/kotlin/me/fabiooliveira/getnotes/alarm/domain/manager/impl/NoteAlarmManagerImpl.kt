package me.fabiooliveira.getnotes.alarm.domain.manager.impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import me.fabiooliveira.getnotes.alarm.broadcastreceiver.NoteAlarmBroadcastReceiver
import me.fabiooliveira.getnotes.alarm.broadcastreceiver.NoteNotificationBeforeBroadcastReceiver
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_CONTENT
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_ID
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_TITLE
import me.fabiooliveira.getnotes.alarm.domain.manager.NoteAlarmManager
import org.koin.core.qualifier.named
import java.util.*

private const val FLAG = 0
private const val ZERO_SECOND = 0
private const val MINUTES_BEFORE = 10
private const val REQUEST_CODE = 1

internal class NoteAlarmManagerImpl(
        private val context: Context,
        private val alarmManager: AlarmManager,
        private val noteAlarmBroadcastReceiver: NoteAlarmBroadcastReceiver,
        private val noteNotificationBeforeBroadcastReceiver: NoteNotificationBeforeBroadcastReceiver,
        private val now: Calendar
) : NoteAlarmManager {
    override fun scheduleAlarm(noteId: Long,
                               noteTitle: String,
                               noteContent: String,
                               calendar: Calendar) {
        prepareNoteBroadcastReceiver(noteId, noteTitle, noteContent, calendar)
        prepareNotificationBeforeBroadcastReceiver(noteTitle, calendar)
    }

    override fun cancelAlarm(noteId: Long) {
        val intent = Intent(named<NoteAlarmBroadcastReceiver>().value)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, noteId.toInt(), intent, FLAG)
        alarmManager.cancel(alarmPendingIntent)
    }

    private fun prepareNoteBroadcastReceiver(noteId: Long,
                                             noteTitle: String,
                                             noteContent: String,
                                             calendar: Calendar) {
        calendar.set(Calendar.SECOND, ZERO_SECOND)

        if (now.before(calendar)) {
            context.registerReceiver(noteAlarmBroadcastReceiver, IntentFilter(named<NoteAlarmBroadcastReceiver>().value))
            Intent(named<NoteAlarmBroadcastReceiver>().value).apply {
                putExtra(NOTE_ID, noteId)
                putExtra(NOTE_TITLE, noteTitle)
                putExtra(NOTE_CONTENT, noteContent)
                val pendingIntent = PendingIntent.getBroadcast(context, noteId.toInt(), this, FLAG)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }

//        intent.putExtra("uri", audioFileUri.toString()) // Here we pass the URI of audio file
        }
    }

    private fun prepareNotificationBeforeBroadcastReceiver(noteTitle: String, calendar: Calendar) {
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - MINUTES_BEFORE)

        if (now.before(calendar)) {
            context.registerReceiver(noteNotificationBeforeBroadcastReceiver, IntentFilter(named<NoteNotificationBeforeBroadcastReceiver>().value))
            Intent(named<NoteNotificationBeforeBroadcastReceiver>().value).apply {
                putExtra(NOTE_TITLE, noteTitle)
                val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, this, FLAG)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        }
    }
}