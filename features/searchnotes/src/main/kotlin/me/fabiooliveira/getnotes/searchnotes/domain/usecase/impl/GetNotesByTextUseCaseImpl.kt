package me.fabiooliveira.getnotes.searchnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.searchnotes.domain.usecase.GetNotesByTextUseCase

internal class GetNotesByTextUseCaseImpl(
        private val noteRepository: NoteRepository
) : GetNotesByTextUseCase {
    override fun invoke(name: String): Flow<List<Note>> {
        return noteRepository.getNotesByText(name)
    }
}