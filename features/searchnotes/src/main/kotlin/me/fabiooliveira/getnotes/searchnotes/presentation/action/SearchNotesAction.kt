package me.fabiooliveira.getnotes.searchnotes.presentation.action

import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

internal sealed class SearchNotesAction {
    data class GoToEditNote(val noteItem: NoteItem) : SearchNotesAction()
}