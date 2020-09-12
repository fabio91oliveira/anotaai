package me.fabiooliveira.getnotes.listnotes.presentation.viewstate

import androidx.annotation.StringRes

internal data class ListNotesViewState(
        @StringRes val tabSelected: Int? = null,
        val isAddButtonVisible: Boolean = false,
        val isHeaderVisible: Boolean = true,
        val isChangeTabEnabled: Boolean = false
) {
    companion object {
        fun init() = ListNotesViewState()
    }
}