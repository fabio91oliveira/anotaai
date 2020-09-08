package me.fabiooliveira.getnotes.domain.usecase

import kotlinx.coroutines.flow.Flow

internal interface ValidateEmptyFieldsUseCase {
    suspend operator fun invoke(
            titleNote: String,
            descriptionNote: String,
            date: String
    ): Flow<Boolean>
}