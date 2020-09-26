package me.fabiooliveira.getnotes.listnotes.domain.usecase

import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

interface MountNoteItemsUseCase {
    suspend operator fun invoke(notesList: List<Note>): List<NoteItem>
}