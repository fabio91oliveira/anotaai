package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.usecase.RemoveNoteUseCase

internal class RemoveNoteUseCaseImpl(
        private val noteRepository: NoteRepository
) : RemoveNoteUseCase {
    override suspend fun invoke(idNote: Long): Flow<Int> {
        return noteRepository.remoteNote(idNote)
    }
}