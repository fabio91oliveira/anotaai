package me.fabiooliveira.getnotes.listnotes.presentation.action

import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

internal sealed class ListNotesAction {
    object GoToCreateNote : ListNotesAction()
    data class GoToEditNote(val noteItem: NoteItem, val viewId: Int) : ListNotesAction()
    object SetDarkMode : ListNotesAction()
    object SetLightMode : ListNotesAction()
    object ShowOnBoarding : ListNotesAction()
}