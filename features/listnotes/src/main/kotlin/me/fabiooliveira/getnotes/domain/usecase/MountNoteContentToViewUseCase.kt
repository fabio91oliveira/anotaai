package me.fabiooliveira.getnotes.domain.usecase

import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.presentation.vo.AdapterItem
import me.fabiooliveira.getnotes.presentation.vo.NoteContent

internal interface MountNoteContentToViewUseCase {
    suspend operator fun invoke(notesList: List<Note>): MutableList<AdapterItem<Pair<String, String>, NoteContent>>
}