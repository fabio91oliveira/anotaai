package me.fabiooliveira.getnotes.searchnotes.presentation.viewstate

import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

internal data class SearchNotesViewState(
        val isLoading: Boolean = false,
        val isContentVisible: Boolean = false,
        val isEmptyState: Boolean = true,
        val isError: Boolean = false,
        val notes: List<NoteItem>? = null,
) {
    companion object {
        fun init() = SearchNotesViewState()
    }
}