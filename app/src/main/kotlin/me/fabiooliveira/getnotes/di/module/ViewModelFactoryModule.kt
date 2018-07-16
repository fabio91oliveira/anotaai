package me.fabiooliveira.getnotes.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.fabiooliveira.getnotes.model.factory.NoteViewModelFactory
import me.fabiooliveira.getnotes.feature.notesList.viewModel.mapper.NoteMapper
import me.fabiooliveira.getnotes.model.repository.LogRepository
import me.fabiooliveira.getnotes.model.repository.NoteRepository
import javax.inject.Singleton

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Module
class ViewModelFactoryModule {
    @Provides
    @Singleton
    fun provideNoteViewModelFactory(noteRepository: NoteRepository, logRepository: LogRepository,
                                    noteMapper: NoteMapper): ViewModelProvider.Factory {
        return NoteViewModelFactory(noteRepository, logRepository, noteMapper)
    }
}