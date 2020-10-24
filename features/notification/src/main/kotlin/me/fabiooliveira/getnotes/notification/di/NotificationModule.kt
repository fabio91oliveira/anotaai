package me.fabiooliveira.getnotes.notification.di

import androidx.work.WorkManager
import me.fabiooliveira.getnotes.notification.domain.analytics.TodayNotesNotificationAnalytics
import me.fabiooliveira.getnotes.notification.domain.analytics.impl.TodayNotesNotificationAnalyticsImpl
import me.fabiooliveira.getnotes.notification.domain.controller.TodayNotesNotificationController
import me.fabiooliveira.getnotes.notification.domain.controller.impl.TodayNotesNotificationControllerImpl
import me.fabiooliveira.getnotes.notification.domain.handler.TodayNotesNotificationWorkerStarter
import me.fabiooliveira.getnotes.notification.domain.handler.impl.TodayNotesNotificationWorkerStarterImpl
import me.fabiooliveira.getnotes.notification.domain.manager.TodayNotesNotificationManager
import me.fabiooliveira.getnotes.notification.domain.manager.impl.TodayNotesNotificationManagerImpl
import me.fabiooliveira.getnotes.notification.domain.usecase.GetNotesOnlyFromTodayUseCase
import me.fabiooliveira.getnotes.notification.domain.usecase.impl.GetNotesOnlyFromTodayUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object NotificationModule {
    private val domainModule = module {
        factory { WorkManager.getInstance(androidContext()) }
        factory<TodayNotesNotificationManager> {
            TodayNotesNotificationManagerImpl(
                    context = androidContext(),
                    listNotesNavigation = get()
            )
        }
        factory<GetNotesOnlyFromTodayUseCase> {
            GetNotesOnlyFromTodayUseCaseImpl(
                    noteRepository = get())
        }
        factory<TodayNotesNotificationAnalytics> {
            TodayNotesNotificationAnalyticsImpl(
                    analytics = get()
            )
        }
        factory<TodayNotesNotificationController> {
            TodayNotesNotificationControllerImpl(
                    getNotesOnlyFromTodayUseCase = get(),
                    todayNotesNotificationManager = get(),
                    todayNotesNotificationAnalytics = get()
            )
        }
        factory<TodayNotesNotificationWorkerStarter> {
            TodayNotesNotificationWorkerStarterImpl(
                    workManager = get()
            )
        }
    }

    fun load() = loadKoinModules(domainModule)
}