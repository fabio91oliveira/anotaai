package me.fabiooliveira.getnotes.domain.usecase

import kotlinx.coroutines.flow.Flow

internal interface RemoveNoteUseCase {
    suspend operator fun invoke(
            idNote: Long
    ): Flow<Int>
}