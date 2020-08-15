package me.fabiooliveira.getnotes.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

object KoinStarter {
    fun start(application: Application) {
        startKoin {
            logger(object : Logger(Level.DEBUG) {
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
            DataAccessModule.load()

            // Features
            ListNotesModule.load()
        }
    }
}