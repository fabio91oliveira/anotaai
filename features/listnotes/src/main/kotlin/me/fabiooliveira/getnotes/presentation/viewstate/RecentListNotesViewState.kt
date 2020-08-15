package me.fabiooliveira.getnotes.presentation.viewstate

import me.fabiooliveira.getnotes.presentation.vo.NoteItem

internal data class RecentListNotesViewState(
        val isLoading: Boolean = false,
        val isAddButtonVisible: Boolean = true,
        val notes: List<NoteItem>? = null
) {
    companion object {
        fun init() = RecentListNotesViewState()
    }
}