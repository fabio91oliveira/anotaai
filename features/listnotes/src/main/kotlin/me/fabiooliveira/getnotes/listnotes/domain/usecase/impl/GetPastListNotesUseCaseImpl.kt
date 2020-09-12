package me.fabiooliveira.getnotes.listnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetPastListNotesUseCase

internal class GetPastListNotesUseCaseImpl(
        private val noteRepository: NoteRepository
) : GetPastListNotesUseCase {
    override suspend operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getNotesBefore()
    }
}