package me.fabiooliveira.anotaai.base

import android.app.Application
import me.fabiooliveira.anotaai.di.component.DaggerMainApplicationComponent
import me.fabiooliveira.anotaai.di.component.MainApplicationComponent
import me.fabiooliveira.anotaai.di.module.MainApplicationModule
import me.fabiooliveira.anotaai.di.module.RepositoryModule
import me.fabiooliveira.anotaai.di.module.RoomModule

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class MainApplication: Application() {

    private lateinit var mainApplicationComponent: MainApplicationComponent

    override fun onCreate() {
        super.onCreate()

        mainApplicationComponent = DaggerMainApplicationComponent
                .builder()
                .mainApplicationModule(MainApplicationModule(this))
                .roomModule(RoomModule(this))
                .repositoryModule(RepositoryModule())
                .build()
    }

    fun getMainApplicationComponent(): MainApplicationComponent {
        return mainApplicationComponent
    }
}