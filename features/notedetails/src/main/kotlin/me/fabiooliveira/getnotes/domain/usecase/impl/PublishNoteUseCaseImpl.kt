package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.domain.usecase.PublishNoteUseCase
import me.fabiooliveira.getnotes.extensions.getDateFromString
import me.fabiooliveira.getnotes.listnotes.presentation.vo.RelevanceEnum

internal class PublishNoteUseCaseImpl(
        private val noteRepository: NoteRepository
) : PublishNoteUseCase {
    override suspend fun invoke(idNote: Long?,
                                titleNote: String,
                                descriptionNote: String,
                                date: String,
                                relevance: RelevanceEnum): Flow<Long> {
        val note = Note(
                id = idNote ?: 0,
                title = titleNote,
                description = descriptionNote,
                date = date.getDateFromString(),
                relevance = relevance.relevanceCode
        )
        return noteRepository.publishNote(note)
    }
}