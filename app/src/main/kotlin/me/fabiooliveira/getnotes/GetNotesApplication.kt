package me.fabiooliveira.getnotes

import android.app.Application
import me.fabiooliveira.getnotes.di.KoinStarter
import me.fabiooliveira.getnotes.notification.domain.handler.NotificationWorkerStarter
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class GetNotesApplication : Application(), KoinComponent {

    private val notificationWorkerStarter: NotificationWorkerStarter by inject()

    override fun onCreate() {
        super.onCreate()
        startTimber()
        startKoin()
        startWorker()
    }

    private fun startTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun startKoin() {
        KoinStarter.start(this)
    }

    private fun startWorker() {
        notificationWorkerStarter.scheduleWorker()
    }
}