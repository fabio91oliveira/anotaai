package me.fabiooliveira.getnotes.presentation.viewstate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import me.fabiooliveira.getnotes.presentation.vo.NoteItem

internal data class RecentListNotesViewState(
        val isLoading: Boolean = false,
        val isContentVisible: Boolean = false,
        val isEmptyState: Boolean = false,
        val notes: List<NoteItem>? = null,
        val dialog: Dialog = Dialog.NoDialog
) {
    sealed class Dialog {
        object NoDialog : Dialog()
        data class ConfirmationDialogRemoveRecentNote(
                @DrawableRes val imageRes: Int,
                @StringRes val titleRes: Int,
                @StringRes val descriptionRes: Int,
                val noteItem: NoteItem
        ) :
                Dialog()
    }

    companion object {
        fun init() = RecentListNotesViewState()
    }
}