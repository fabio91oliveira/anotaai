package me.fabiooliveira.getnotes.presentation.action

import java.util.*

internal sealed class NoteDetailsAction {
    object Success : NoteDetailsAction()
    object Error : NoteDetailsAction()
    object CloseScreen : NoteDetailsAction()
    data class UpdateDate(val cal: Calendar) : NoteDetailsAction()
    data class UpdateTime(val cal: Calendar) : NoteDetailsAction()
    data class SetAlarm(val noteId: Long,
                        val noteTitle: String,
                        val noteContent: String,
                        val cal: Calendar) : NoteDetailsAction()

    data class CancelAlarm(val noteId: Long) : NoteDetailsAction()
}