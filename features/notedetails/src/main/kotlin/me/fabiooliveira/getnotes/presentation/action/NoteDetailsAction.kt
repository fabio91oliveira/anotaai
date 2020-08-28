package me.fabiooliveira.getnotes.presentation.action

internal sealed class NoteDetailsAction {
    object Success : NoteDetailsAction()
    object Error : NoteDetailsAction()
    object CloseScreen : NoteDetailsAction()
}