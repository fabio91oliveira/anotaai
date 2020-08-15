package me.fabiooliveira.getnotes.domain.usecase

import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.presentation.vo.NoteItem

internal interface MountNoteItemsUseCase {
    suspend operator fun invoke(notesList: List<Note>): List<NoteItem>
}