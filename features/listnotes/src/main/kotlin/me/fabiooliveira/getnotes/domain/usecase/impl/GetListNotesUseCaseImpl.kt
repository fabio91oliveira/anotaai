package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.usecase.GetListNotesUseCase

internal class GetListNotesUseCaseImpl(
        private val notesRepository: NoteRepository
) : GetListNotesUseCase {
    override suspend operator fun invoke() = withContext(Dispatchers.IO) {
        notesRepository.getNotes()
    }
}