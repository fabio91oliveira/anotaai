package me.fabiooliveira.getnotes.domain.usecase

import me.fabiooliveira.getnotes.presentation.vo.RelevanceEnum

internal interface PublishNoteUseCase {
    suspend operator fun invoke(
            idNote: Long? = null,
            titleNote: String,
            descriptionNote: String,
            date: String,
            relevance: RelevanceEnum
    )
}