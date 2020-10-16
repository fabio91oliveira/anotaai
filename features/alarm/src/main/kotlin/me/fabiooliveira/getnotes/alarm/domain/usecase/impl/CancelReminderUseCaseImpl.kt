package me.fabiooliveira.getnotes.alarm.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.alarm.domain.usecase.CancelReminderUseCase
import me.fabiooliveira.getnotes.data.repository.NoteRepository

internal class CancelReminderUseCaseImpl(
        private val noteRepository: NoteRepository
) : CancelReminderUseCase {
    override suspend fun invoke(noteId: Long): Flow<Int> {
        return noteRepository.cancelReminder(noteId)
    }
}