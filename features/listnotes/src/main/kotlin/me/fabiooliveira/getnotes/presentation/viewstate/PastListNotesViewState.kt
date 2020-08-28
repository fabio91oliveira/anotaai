package me.fabiooliveira.getnotes.presentation.viewstate

import me.fabiooliveira.getnotes.presentation.vo.NoteItem

internal data class PastListNotesViewState(
        val isLoading: Boolean = false,
        val isContentVisible: Boolean = false,
        val isEmptyState: Boolean = false,
        val notes: List<NoteItem>? = null
) {
    companion object {
        fun init() = PastListNotesViewState()
    }
}