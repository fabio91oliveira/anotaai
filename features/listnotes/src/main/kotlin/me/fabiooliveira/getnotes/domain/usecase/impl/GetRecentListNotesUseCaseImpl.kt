package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.usecase.GetRecentListNotesUseCase

internal class GetRecentListNotesUseCaseImpl(
        private val noteRepository: NoteRepository
) : GetRecentListNotesUseCase {
    override suspend operator fun invoke() = withContext(Dispatchers.IO) {
        noteRepository.getNotesFromTodayToFuture()
    }
}