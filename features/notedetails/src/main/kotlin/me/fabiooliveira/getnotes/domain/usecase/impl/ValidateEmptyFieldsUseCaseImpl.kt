package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.fabiooliveira.getnotes.domain.usecase.ValidateEmptyFieldsUseCase

internal class ValidateEmptyFieldsUseCaseImpl : ValidateEmptyFieldsUseCase {
    override suspend fun invoke(
            titleNote: String,
            descriptionNote: String,
            date: String,
            time: String
    ): Flow<Boolean> {
        return flow {
            emit(titleNote.isNotEmpty() && descriptionNote.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty())
        }
    }
}