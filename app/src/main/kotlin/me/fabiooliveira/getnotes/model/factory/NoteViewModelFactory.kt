package me.fabiooliveira.getnotes.model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.fabiooliveira.getnotes.feature.noteAdd.viewModel.NoteAddViewModel
import me.fabiooliveira.getnotes.feature.notesList.viewModel.NotesListViewModel
import me.fabiooliveira.getnotes.feature.notesList.viewModel.mapper.NoteMapper
import me.fabiooliveira.getnotes.model.repository.LogRepository
import me.fabiooliveira.getnotes.model.repository.NoteRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

@Singleton
class NoteViewModelFactory @Inject constructor(private val noteRepository: NoteRepository, private val logRepository: LogRepository,
                                               private val noteMapper: NoteMapper): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NotesListViewModel::class.java) -> NotesListViewModel(noteRepository, logRepository, noteMapper) as T
            modelClass.isAssignableFrom(NoteAddViewModel::class.java) -> NoteAddViewModel(noteRepository) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}