package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.data.repository.NoteRepository

internal class GetListNotesUseCase(
        private val notesRepository: NoteRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        notesRepository.getNotes()
    }
}