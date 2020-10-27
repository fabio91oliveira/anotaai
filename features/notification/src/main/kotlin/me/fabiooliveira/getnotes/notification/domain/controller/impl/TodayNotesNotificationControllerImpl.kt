package me.fabiooliveira.getnotes.notification.domain.controller.impl

import me.fabiooliveira.getnotes.notification.domain.analytics.TodayNotesNotificationAnalytics
import me.fabiooliveira.getnotes.notification.domain.controller.TodayNotesNotificationController
import me.fabiooliveira.getnotes.notification.domain.manager.TodayNotesNotificationManager
import me.fabiooliveira.getnotes.notification.domain.usecase.GetNotesOnlyFromTodayUseCase

internal class TodayNotesNotificationControllerImpl(
        private val getNotesOnlyFromTodayUseCase: GetNotesOnlyFromTodayUseCase,
        private val todayNotesNotificationManager: TodayNotesNotificationManager,
        private val todayNotesNotificationAnalytics: TodayNotesNotificationAnalytics) : TodayNotesNotificationController {

    override fun handleNotesNotification() {
        val listNotesFromToday = getNotesOnlyFromTodayUseCase()
        if (listNotesFromToday.isNotEmpty()) {
            todayNotesNotificationManager.showNotification()
            todayNotesNotificationAnalytics.trackNotified()
        }
    }
}