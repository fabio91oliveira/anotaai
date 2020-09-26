package me.fabiooliveira.getnotes.listnotes.presentation.action

import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

internal sealed class ListNotesAction {
    object GoToCreateNote : ListNotesAction()
    data class GoToEditNote(val noteItem: NoteItem) : ListNotesAction()
    object SetDarkMode : ListNotesAction()
    object SetLightMode : ListNotesAction()
    object ShowOnBoarding : ListNotesAction()
}