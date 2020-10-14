package me.fabiooliveira.getnotes.notification.domain.controller.impl

import me.fabiooliveira.getnotes.notification.domain.controller.NotificationController
import me.fabiooliveira.getnotes.notification.domain.manager.TodayNotesNotificationManager
import me.fabiooliveira.getnotes.notification.domain.usecase.GetNotesOnlyFromTodayUseCase

internal class NotificationControllerImpl(
        private val getNotesOnlyFromTodayUseCase: GetNotesOnlyFromTodayUseCase,
        private val todayNotesNotificationManager: TodayNotesNotificationManager) : NotificationController {

    override fun handleNotesNotification() {
        val listNotesFromToday = getNotesOnlyFromTodayUseCase()
        if (listNotesFromToday.isNotEmpty()) todayNotesNotificationManager.showNotification()
    }
}