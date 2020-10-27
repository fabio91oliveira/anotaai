package me.fabiooliveira.getnotes.alarm.domain.manager.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.alarm.domain.manager.ReminderStatusManager
import me.fabiooliveira.getnotes.alarm.domain.usecase.CancelReminderUseCase
import kotlin.coroutines.CoroutineContext

internal class ReminderStatusManagerImpl(
        private val cancelReminderUseCase: CancelReminderUseCase
) : ReminderStatusManager, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + Job()

    override fun cancelReminder(noteId: Long) {
        launch {
            cancelReminderUseCase(noteId)
                    .catch {

                    }
                    .collect {

                    }
        }
    }
}