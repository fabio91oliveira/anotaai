package me.fabiooliveira.getnotes.alarm.service

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import features.alarm.R
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_TITLE
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

private const val REQUEST_CODE_APP = 2

internal class NoteNotificationBeforeService : IntentService(named<NoteNotificationBeforeService>().value), KoinComponent {

    private val listNotesNavigation: ListNotesNavigation by inject()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onHandleIntent(intent: Intent) {
        val noteTitle = intent.extras?.getString(NOTE_TITLE).orEmpty()
        showNotification(noteTitle)
    }

    private fun showNotification(noteTitle: String) {
        val notificationManager = NotificationManagerCompat.from(this)
        val channelId = named<NoteNotificationBeforeService>().javaClass.toString()
        val channelName = named<NoteNotificationBeforeService>().value

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val appIntent = listNotesNavigation.getIntent(this)
        appIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingAppIntent = PendingIntent.getActivity(this, REQUEST_CODE_APP, appIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, channelId).apply {
            setContentTitle(getString(R.string.alarm_feature_reminder_title, noteTitle))
            setContentText(getString(R.string.alarm_feature_reminder_before_description))
            setSmallIcon(R.mipmap.ic_launcher)
            setContentIntent(pendingAppIntent)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_MAX
        }.build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}