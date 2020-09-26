package me.fabiooliveira.getnotes.searchnotes.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.domain.model.Note

internal interface GetNotesByTextUseCase {
    operator fun invoke(name: String): Flow<List<Note>>
}