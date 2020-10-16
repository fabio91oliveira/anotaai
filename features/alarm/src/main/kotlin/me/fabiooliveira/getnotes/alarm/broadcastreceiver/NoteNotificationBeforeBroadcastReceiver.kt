package me.fabiooliveira.getnotes.alarm.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_CONTENT
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_TITLE
import me.fabiooliveira.getnotes.alarm.service.NoteNotificationBeforeService

class NoteNotificationBeforeBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        startAlarmService(context, intent)
    }

    private fun startAlarmService(context: Context, intent: Intent) {
        val intentService = Intent(context, NoteNotificationBeforeService::class.java)
        intentService.putExtra(NOTE_TITLE, intent.getStringExtra(NOTE_TITLE))
        intentService.putExtra(NOTE_CONTENT, intent.getStringExtra(NOTE_CONTENT))
        context.startService(intentService)
    }
}