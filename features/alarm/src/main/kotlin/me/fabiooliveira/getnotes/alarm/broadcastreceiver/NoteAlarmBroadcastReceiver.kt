package me.fabiooliveira.getnotes.alarm.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_CONTENT
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_ID
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_TITLE
import me.fabiooliveira.getnotes.alarm.service.NoteAlarmService

private const val DEFAULT_VALUE = 0L

class NoteAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        startAlarmService(context, intent)
    }

    private fun startAlarmService(context: Context, intent: Intent) {
        val intentService = Intent(context, NoteAlarmService::class.java)
        intentService.putExtra(NOTE_ID, intent.getLongExtra(NOTE_ID, DEFAULT_VALUE))
        intentService.putExtra(NOTE_TITLE, intent.getStringExtra(NOTE_TITLE))
        intentService.putExtra(NOTE_CONTENT, intent.getStringExtra(NOTE_CONTENT))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }
}