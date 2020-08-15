//package me.fabiooliveira.getnotes.legacy.di.module
//
//import android.app.Application
//import dagger.Module
//import dagger.Provides
//import me.fabiooliveira.getnotes.legacy.base.MainApplication
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//@Module
//class MainApplicationModule(private val mainApplication: MainApplication) {
//    @Provides
//    fun provideMainApplication(): MainApplication {
//        return mainApplication
//    }
//
//    @Provides
//    fun provideApplication(): Application {
//        return mainApplication
//    }
//}