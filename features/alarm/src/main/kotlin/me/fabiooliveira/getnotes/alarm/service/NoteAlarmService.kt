package me.fabiooliveira.getnotes.alarm.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import features.alarm.R
import me.fabiooliveira.getnotes.alarm.domain.manager.CHANNEL_ID
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_CONTENT
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_ID
import me.fabiooliveira.getnotes.alarm.domain.manager.NOTE_TITLE
import me.fabiooliveira.getnotes.alarm.domain.manager.ReminderStatusManager
import org.koin.core.KoinComponent
import org.koin.core.inject


private const val LOOPING_DEFAULT_VALUE = true
private const val DEFAULT_NOTE_ID = 0L
private const val DEFAULT_TIME = 2000L
private const val ACTION_STOP = "STOP"
private const val REQUEST_CODE = 0

internal class NoteAlarmService : Service(), KoinComponent {

    private val mediaPlayer: MediaPlayer by inject()
    private val vibrator: Vibrator by inject()
    private val reminderStatusManager: ReminderStatusManager by inject()

    override fun onCreate() {
        super.onCreate()
        configAudioLooping()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.action == ACTION_STOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopForeground(true)
            }
            stopSelf()
        } else {
            val noteId = intent.extras?.getLong(NOTE_ID, DEFAULT_NOTE_ID) ?: 0L
            val noteContent = intent.extras?.getString(NOTE_CONTENT).orEmpty()
            val noteTitle = intent.extras?.getString(NOTE_TITLE).orEmpty()

            startSound()
            startVibration()
            showNotification(noteTitle, noteContent)

            reminderStatusManager.cancelReminder(noteId)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun configAudioLooping() {
        mediaPlayer.isLooping = LOOPING_DEFAULT_VALUE
    }

    private fun showNotification(noteTitle: String,
                                 noteContent: String) {

        val stopNotificationIntent = Intent(this, NoteAlarmService::class.java)
        stopNotificationIntent.action = ACTION_STOP

        val stopIntent = PendingIntent.getService(this, REQUEST_CODE, stopNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(noteTitle)
            setContentText(noteContent)
            addAction(android.R.drawable.ic_media_pause, getString(R.string.alarm_feature_alarm_stop), stopIntent)
            priority = NotificationCompat.PRIORITY_MAX
        }.build()
        startForeground(START_STICKY, notification)
    }

    private fun startSound() {
        mediaPlayer.start()
    }

    private fun startVibration() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(DEFAULT_TIME, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(DEFAULT_TIME)
        }
    }
}