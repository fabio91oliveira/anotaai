package me.fabiooliveira.getnotes.notification.domain.manager.impl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import features.notification.R
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import me.fabiooliveira.getnotes.notification.domain.manager.TodayNotesNotificationManager
import org.koin.core.qualifier.named

private const val REQUEST_CODE = 0

internal class TodayNotesNotificationManagerImpl(
        private val context: Context,
        private val listNotesNavigation: ListNotesNavigation) : TodayNotesNotificationManager {

    override fun showNotification() {
        val notificationManager = NotificationManagerCompat.from(context)
        val channelId = named<TodayNotesNotificationManagerImpl>().javaClass.toString()
        val channelName = named<TodayNotesNotificationManagerImpl>().value

        val intent = listNotesNavigation.getIntent(context)
        val pIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId).apply {
            setContentTitle(context.getString(R.string.notification_feature_push_notes_today_title))
            setContentText(context.getString(R.string.notification_feature_push_notes_today_message))
            setSmallIcon(R.mipmap.ic_launcher)
            setContentIntent(pIntent)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_MAX
        }.build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}