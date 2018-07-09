package me.fabiooliveira.anotaai.di.component

import android.app.Application
import dagger.Component
import me.fabiooliveira.anotaai.di.module.*
import me.fabiooliveira.anotaai.feature.noteAdd.ui.activity.NoteAddActivity
import me.fabiooliveira.anotaai.feature.notesList.ui.activity.NotesListActivity
import javax.inject.Singleton

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Singleton
@Component(modules = [(MainApplicationModule::class), (RoomModule::class),
    (MapperModule::class), (RepositoryModule::class), (ViewModelFactoryModule::class)])
interface MainApplicationComponent {
    fun inject(notesListActivity: NotesListActivity)
    fun inject(noteAddActivity: NoteAddActivity)
    fun application(): Application
}