package me.fabiooliveira.getnotes.notification.domain.handler.impl

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import me.fabiooliveira.getnotes.notification.domain.handler.NotificationWorkerStarter
import me.fabiooliveira.getnotes.notification.domain.worker.NotificationWorker
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

private const val TIME_REPEAT = 8L

internal class NotificationWorkerStarterImpl(
        private val workManager: WorkManager
) : NotificationWorkerStarter {
    override fun scheduleWorker() {
        PeriodicWorkRequestBuilder<NotificationWorker>(
                TIME_REPEAT, TimeUnit.HOURS)
                .build().apply {
                    workManager.enqueueUniquePeriodicWork(
                            named<NotificationWorker>().value,
                            ExistingPeriodicWorkPolicy.REPLACE,
                            this)
                }
    }
}