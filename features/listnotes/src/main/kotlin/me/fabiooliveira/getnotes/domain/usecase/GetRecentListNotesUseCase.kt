package me.fabiooliveira.getnotes.domain.usecase

import me.fabiooliveira.getnotes.domain.model.Note

internal interface GetRecentListNotesUseCase {
    suspend operator fun invoke(): List<Note>
}