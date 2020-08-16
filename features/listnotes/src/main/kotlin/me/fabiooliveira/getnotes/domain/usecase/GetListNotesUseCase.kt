package me.fabiooliveira.getnotes.domain.usecase

import me.fabiooliveira.getnotes.domain.model.Note

internal interface GetListNotesUseCase {
    suspend operator fun invoke(): List<Note>
}