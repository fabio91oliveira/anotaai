package me.fabiooliveira.getnotes.presentation.viewstate

import androidx.annotation.StringRes

internal data class ListNotesViewState(
        @StringRes val tabSelected: Int? = null
) {
    companion object {
        fun init() = ListNotesViewState()
    }
}