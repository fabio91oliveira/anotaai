package me.fabiooliveira.getnotes

import android.app.Application
import me.fabiooliveira.getnotes.di.KoinStarter
import timber.log.Timber

class GetNotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startTimber()
        startKoin()
    }

    private fun startTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun startKoin() {
        KoinStarter.start(this)
    }
}