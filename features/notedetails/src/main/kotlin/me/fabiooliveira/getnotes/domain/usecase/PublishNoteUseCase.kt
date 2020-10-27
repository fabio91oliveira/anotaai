package me.fabiooliveira.getnotes.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.listnotes.presentation.vo.RelevanceEnum
import java.util.*

internal interface PublishNoteUseCase {
    suspend operator fun invoke(
            idNote: Long? = null,
            titleNote: String,
            descriptionNote: String,
            calendar: Calendar,
            relevance: RelevanceEnum,
            isReminder: Boolean
    ): Flow<Long>
}