package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.domain.usecase.PublishNoteUseCase
import me.fabiooliveira.getnotes.listnotes.presentation.vo.RelevanceEnum
import java.util.*

internal class PublishNoteUseCaseImpl(
        private val noteRepository: NoteRepository
) : PublishNoteUseCase {
    override suspend fun invoke(idNote: Long?,
                                titleNote: String,
                                descriptionNote: String,
                                calendar: Calendar,
                                relevance: RelevanceEnum,
                                isReminder: Boolean): Flow<Long> {
        val note = Note(
                id = idNote ?: 0,
                title = titleNote,
                description = descriptionNote,
                date = calendar.time,
                relevance = relevance.relevanceCode,
                isReminder = isReminder
        )
        return noteRepository.publishNote(note)
    }
}