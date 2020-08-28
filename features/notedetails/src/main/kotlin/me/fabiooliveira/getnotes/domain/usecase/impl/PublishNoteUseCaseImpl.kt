package me.fabiooliveira.getnotes.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.fabiooliveira.getnotes.data.repository.NoteRepository
import me.fabiooliveira.getnotes.domain.usecase.PublishNoteUseCase
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.extensions.getDateFromString
import me.fabiooliveira.getnotes.presentation.vo.RelevanceEnum

internal class PublishNoteUseCaseImpl(
        private val noteRepository: NoteRepository
) : PublishNoteUseCase {
    override suspend fun invoke(idNote: Long?,
                                titleNote: String,
                                descriptionNote: String,
                                date: String,
                                relevance: RelevanceEnum) = withContext(Dispatchers.IO) {
        val note = Note(
                id = idNote ?: 0,
                title = titleNote,
                description = descriptionNote,
                date = date.getDateFromString(),
                relevance = relevance.relevanceCode
        )
        noteRepository.publishNote(note)
    }
}