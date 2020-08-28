package me.fabiooliveira.getnotes.presentation.action

import me.fabiooliveira.getnotes.presentation.vo.NoteItem

internal sealed class ListNotesAction {
    object GoToCreateNote : ListNotesAction()
    data class GoToEditNote(val noteItem: NoteItem, val viewId: Int) : ListNotesAction()
}