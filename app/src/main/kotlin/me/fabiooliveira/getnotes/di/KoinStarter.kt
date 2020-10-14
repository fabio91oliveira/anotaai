package me.fabiooliveira.getnotes.di

import android.app.Application
import me.fabiooliveira.getnotes.notification.di.NotificationModule
import me.fabiooliveira.getnotes.searchnotes.di.SearchNotesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

object KoinStarter {
    fun start(application: Application) {
        startKoin {
            logger(object : Logger(Level.ERROR) {
                override fun log(
                        level: Level,
                        msg: MESSAGE
                ) {
                    when (level) {
                        Level.DEBUG -> Timber.d(msg)
                        Level.INFO -> Timber.i(msg)
                        Level.ERROR -> Timber.e(msg)
                        Level.NONE -> {
                        }
                    }
                }
            })
            androidContext(application)

            // Data
            AppModule.load()
            DataAccessModule.load()

            // Features
            ListNotesModule.load()
            NoteDetailsModule.load()
            SearchNotesModule.load()
            NotificationModule.load()
        }
    }
}