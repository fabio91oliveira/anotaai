package me.fabiooliveira.getnotes.listnotes.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.domain.model.Note

internal interface GetPastListNotesUseCase {
    suspend operator fun invoke(): Flow<List<Note>>
}