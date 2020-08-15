//package me.fabiooliveira.getnotes.legacy.di.component
//
//import android.app.Application
//import dagger.Component
//import me.fabiooliveira.getnotes.di.module.*
//import me.fabiooliveira.getnotes.legacy.di.module.MainApplicationModule
//import me.fabiooliveira.getnotes.legacy.di.module.MapperModule
//import me.fabiooliveira.getnotes.legacy.di.module.RepositoryModule
//import me.fabiooliveira.getnotes.legacy.di.module.RoomModule
//import me.fabiooliveira.getnotes.legacy.di.module.ViewModelFactoryModule
//import me.fabiooliveira.getnotes.legacy.feature.noteAdd.ui.activity.NoteAddActivity
//import me.fabiooliveira.getnotes.legacy.feature.notesList.ui.activity.NotesListActivity
//import javax.inject.Singleton
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//@Singleton
//@Component(modules = [(MainApplicationModule::class), (RoomModule::class),
//    (MapperModule::class), (RepositoryModule::class), (ViewModelFactoryModule::class)])
//interface MainApplicationComponent {
//    fun inject(notesListActivity: NotesListActivity)
//    fun inject(noteAddActivity: NoteAddActivity)
//    fun application(): Application
//}