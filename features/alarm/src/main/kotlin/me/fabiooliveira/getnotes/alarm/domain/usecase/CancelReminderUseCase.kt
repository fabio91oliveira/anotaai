package me.fabiooliveira.getnotes.alarm.domain.usecase

import kotlinx.coroutines.flow.Flow

internal interface CancelReminderUseCase {
    suspend operator fun invoke(noteId: Long): Flow<Int>
}