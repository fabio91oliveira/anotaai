package me.fabiooliveira.getnotes.notification.domain.usecase.impl

import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.notification.domain.usecase.GetNotesOnlyFromTodayUseCase

internal class GetNotesOnlyFromTodayUseCaseImpl(
        private val noteRepository: NoteRepository
) : GetNotesOnlyFromTodayUseCase {
    override fun invoke(): List<Note> {
        return noteRepository.getNotesOnlyFromToday()
    }
}