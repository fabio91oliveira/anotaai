package me.fabiooliveira.getnotes.notification.di

import androidx.work.WorkManager
import me.fabiooliveira.getnotes.notification.domain.controller.NotificationController
import me.fabiooliveira.getnotes.notification.domain.controller.impl.NotificationControllerImpl
import me.fabiooliveira.getnotes.notification.domain.handler.NotificationWorkerStarter
import me.fabiooliveira.getnotes.notification.domain.handler.impl.NotificationWorkerStarterImpl
import me.fabiooliveira.getnotes.notification.domain.manager.TodayNotesNotificationManager
import me.fabiooliveira.getnotes.notification.domain.usecase.GetNotesOnlyFromTodayUseCase
import me.fabiooliveira.getnotes.notification.domain.usecase.impl.GetNotesOnlyFromTodayUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object NotificationModule {
    private val domainModule = module {
        factory { WorkManager.getInstance(androidContext()) }
        factory {
            TodayNotesNotificationManager(
                    context = androidContext(),
                    listNotesNavigation = get()
            )
        }
        factory<GetNotesOnlyFromTodayUseCase> {
            GetNotesOnlyFromTodayUseCaseImpl(
                    noteRepository = get())
        }
        factory<NotificationController> {
            NotificationControllerImpl(
                    getNotesOnlyFromTodayUseCase = get(),
                    todayNotesNotificationManager = get()
            )
        }
        factory<NotificationWorkerStarter> {
            NotificationWorkerStarterImpl(
                    workManager = get()
            )
        }
    }

    fun load() = loadKoinModules(domainModule)
}