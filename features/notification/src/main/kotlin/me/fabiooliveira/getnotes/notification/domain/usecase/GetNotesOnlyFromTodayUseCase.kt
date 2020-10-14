package me.fabiooliveira.getnotes.notification.domain.usecase

import me.fabiooliveira.getnotes.domain.model.Note

internal interface GetNotesOnlyFromTodayUseCase {
    operator fun invoke(): List<Note>
}