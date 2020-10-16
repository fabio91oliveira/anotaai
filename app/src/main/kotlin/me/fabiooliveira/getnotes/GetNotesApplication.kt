package me.fabiooliveira.getnotes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import me.fabiooliveira.getnotes.alarm.domain.manager.CHANNEL_ID
import me.fabiooliveira.getnotes.alarm.domain.manager.CHANNEL_NAME
import me.fabiooliveira.getnotes.di.KoinStarter
import me.fabiooliveira.getnotes.notification.domain.handler.TodayNotesNotificationWorkerStarter
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class GetNotesApplication : Application(), KoinComponent {

    private val todayNotesNotificationWorkerStarter: TodayNotesNotificationWorkerStarter by inject()

    override fun onCreate() {
        super.onCreate()
        startTimber()
        startKoin()
        startWorker()
        createNotificationChannel()
    }

    private fun startTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun startKoin() {
        KoinStarter.start(this)
    }

    private fun startWorker() {
        todayNotesNotificationWorkerStarter.scheduleWorker()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}