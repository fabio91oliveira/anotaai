package me.fabiooliveira.getnotes.notification.domain.handler.impl

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import me.fabiooliveira.getnotes.notification.domain.handler.TodayNotesNotificationWorkerStarter
import me.fabiooliveira.getnotes.notification.domain.worker.TodayNotesNotificationWorker
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

private const val TIME_REPEAT = 8L

internal class TodayNotesNotificationWorkerStarterImpl(
        private val workManager: WorkManager
) : TodayNotesNotificationWorkerStarter {
    override fun scheduleWorker() {
        PeriodicWorkRequestBuilder<TodayNotesNotificationWorker>(
                TIME_REPEAT, TimeUnit.HOURS)
                .build().apply {
                    workManager.enqueueUniquePeriodicWork(
                            named<TodayNotesNotificationWorker>().value,
                            ExistingPeriodicWorkPolicy.KEEP,
                            this)
                }
    }
}