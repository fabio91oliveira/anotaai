package me.fabiooliveira.getnotes.notification.domain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.notification.domain.controller.TodayNotesNotificationController
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class TodayNotesNotificationWorker(context: Context,
                                            params: WorkerParameters)
    : CoroutineWorker(context, params), KoinComponent {

    private val todayNotesNotificationController: TodayNotesNotificationController by inject()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            todayNotesNotificationController.handleNotesNotification()
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
}