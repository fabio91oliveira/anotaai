package me.fabiooliveira.getnotes.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.presentation.vo.RelevanceEnum

internal interface PublishNoteUseCase {
    suspend operator fun invoke(
            idNote: Long? = null,
            titleNote: String,
            descriptionNote: String,
            date: String,
            relevance: RelevanceEnum
    ): Flow<Long>
}