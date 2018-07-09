package me.fabiooliveira.anotaai.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.fabiooliveira.anotaai.model.factory.NoteViewModelFactory
import me.fabiooliveira.anotaai.feature.notesList.viewModel.mapper.NoteMapper
import me.fabiooliveira.anotaai.model.repository.LogRepository
import me.fabiooliveira.anotaai.model.repository.NoteRepository
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