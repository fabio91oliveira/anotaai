package me.fabiooliveira.getnotes.listnotes.presentation.viewstate

import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

internal data class RecentListNotesViewState(
        val isLoading: Boolean = false,
        val isContentVisible: Boolean = false,
        val isEmptyState: Boolean = false,
        val notes: List<NoteItem>? = null
) {
    companion object {
        fun init() = RecentListNotesViewState()
    }
}