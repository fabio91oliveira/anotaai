package me.fabiooliveira.getnotes.domain.usecase

import me.fabiooliveira.getnotes.domain.model.Note

internal interface GetPastListNotesUseCase {
    suspend operator fun invoke(): List<Note>
}