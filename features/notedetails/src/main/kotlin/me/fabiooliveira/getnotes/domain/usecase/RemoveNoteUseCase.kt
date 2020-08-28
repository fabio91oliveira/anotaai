package me.fabiooliveira.getnotes.domain.usecase

internal interface RemoveNoteUseCase {
    suspend operator fun invoke(
            idNote: Long
    )
}