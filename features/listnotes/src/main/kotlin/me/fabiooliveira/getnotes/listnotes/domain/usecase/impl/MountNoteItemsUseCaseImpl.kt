package me.fabiooliveira.getnotes.listnotes.domain.usecase.impl

import android.text.format.DateUtils
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.extensions.getDateString
import me.fabiooliveira.getnotes.extensions.getNameOfTheDay
import me.fabiooliveira.getnotes.listnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem
import me.fabiooliveira.getnotes.listnotes.presentation.vo.RelevanceEnum

internal class MountNoteItemsUseCaseImpl : MountNoteItemsUseCase {
    override suspend fun invoke(notesList: List<Note>) = notesList.map(::transform)

    private fun transform(note: Note) =
            NoteItem(
                    id = note.id,
                    title = note.title,
                    description = note.description,
                    date = note.date.getDateString(),
                    dateName = note.date.getNameOfTheDay(),
                    relevance = getRelevance(note.relevance),
                    isToday = DateUtils.isToday(note.date.time)
            )

    private fun getRelevance(relevance: Int): RelevanceEnum {
        return when (relevance) {
            RelevanceEnum.HIGH.relevanceCode -> {
                RelevanceEnum.HIGH
            }
            RelevanceEnum.MEDIUM.relevanceCode -> {
                RelevanceEnum.MEDIUM
            }
            else -> RelevanceEnum.NORMAL
        }
    }
}