package me.fabiooliveira.getnotes.listnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetRecentListNotesUseCase

internal class GetRecentListNotesUseCaseImpl(
        private val noteRepository: NoteRepository
) : GetRecentListNotesUseCase {
    override suspend operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getNotesFromTodayToFuture()
    }
}