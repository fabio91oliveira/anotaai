package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.usecase.GetPastListNotesUseCase

internal class GetPastListNotesUseCaseImpl(
        private val noteRepository: NoteRepository
) : GetPastListNotesUseCase {
    override suspend operator fun invoke() = withContext(Dispatchers.IO) {
        noteRepository.getNotesBefore()
    }
}