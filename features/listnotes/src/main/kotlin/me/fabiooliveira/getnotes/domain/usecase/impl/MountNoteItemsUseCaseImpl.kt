package me.fabiooliveira.getnotes.domain.usecase.impl

import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.presentation.vo.NoteItem

internal class MountNoteItemsUseCaseImpl : MountNoteItemsUseCase {
    override suspend fun invoke(notesList: List<Note>): List<NoteItem> {
        return notesList.map(::transform)
    }

    private fun transform(note: Note) =
            NoteItem(
                    id = note.id,
                    title = note.title,
                    description = note.description,
                    date = "23/05/2020",
                    isDone = note.isDone
            )
}