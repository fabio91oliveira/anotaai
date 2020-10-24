package me.fabiooliveira.getnotes.alarm.di

import android.app.AlarmManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Vibrator
import features.alarm.R
import me.fabiooliveira.getnotes.alarm.broadcastreceiver.NoteAlarmBroadcastReceiver
import me.fabiooliveira.getnotes.alarm.domain.analytics.NoteAlarmAnalytics
import me.fabiooliveira.getnotes.alarm.domain.analytics.impl.NoteAlarmAnalyticsImpl
import me.fabiooliveira.getnotes.alarm.domain.manager.NoteAlarmManager
import me.fabiooliveira.getnotes.alarm.domain.manager.ReminderStatusManager
import me.fabiooliveira.getnotes.alarm.domain.manager.impl.NoteAlarmManagerImpl
import me.fabiooliveira.getnotes.alarm.domain.manager.impl.ReminderStatusManagerImpl
import me.fabiooliveira.getnotes.alarm.domain.usecase.CancelReminderUseCase
import me.fabiooliveira.getnotes.alarm.domain.usecase.impl.CancelReminderUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.util.*

object AlarmModule {
    private val domainModule = module {
        factory<NoteAlarmAnalytics> {
            NoteAlarmAnalyticsImpl(
                    analytics = get()
            )
        }
        factory<NoteAlarmManager> {
            NoteAlarmManagerImpl(
                    now = Calendar.getInstance(),
                    context = androidContext(),
                    alarmManager = androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager,
                    noteAlarmBroadcastReceiver = NoteAlarmBroadcastReceiver(),
                    noteAlarmAnalytics = get()
            )
        }
        factory<CancelReminderUseCase> {
            CancelReminderUseCaseImpl(
                    noteRepository = get()
            )
        }
        factory<ReminderStatusManager> {
            ReminderStatusManagerImpl(
                    cancelReminderUseCase = get()
            )
        }
    }
    private val alarmModule = module {
        factory { MediaPlayer.create(androidContext(), R.raw.alarm) }
        factory { androidContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    }

    fun load() = loadKoinModules(listOf(domainModule, alarmModule))
}