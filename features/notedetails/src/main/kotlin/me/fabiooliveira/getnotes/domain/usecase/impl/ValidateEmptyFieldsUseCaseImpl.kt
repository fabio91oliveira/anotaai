package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.domain.usecase.ValidateEmptyFieldsUseCase

internal class ValidateEmptyFieldsUseCaseImpl : ValidateEmptyFieldsUseCase {
    override suspend fun invoke(
            titleNote: String,
            descriptionNote: String,
            date: String
    ) = withContext(Dispatchers.Default) {
        titleNote.isNotEmpty() && descriptionNote.isNotEmpty() && date.isNotEmpty()
    }
}