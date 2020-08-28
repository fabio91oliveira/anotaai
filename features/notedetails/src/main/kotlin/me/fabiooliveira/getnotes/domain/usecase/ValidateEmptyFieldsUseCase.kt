package me.fabiooliveira.getnotes.domain.usecase

internal interface ValidateEmptyFieldsUseCase {
    suspend operator fun invoke(
            titleNote: String,
            descriptionNote: String,
            date: String
    ): Boolean
}