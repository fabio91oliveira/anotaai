package me.fabiooliveira.getnotes.presentation.viewstate

import androidx.annotation.StringRes

internal data class NoteDetailsViewState(
        val isLoading: Boolean = false,
        val dialog: Dialog = Dialog.NoDialog
) {
    sealed class Dialog {
        object NoDialog : Dialog()
        data class ConfirmationDialogRemoveNote(
                @StringRes val titleRes: Int,
                @StringRes val descriptionRes: Int
        ) :
                Dialog()

        data class EmptyFieldsDialog(
                @StringRes val titleRes: Int,
                @StringRes val descriptionRes: Int
        ) :
                Dialog()
    }

    companion object {
        fun init() = NoteDetailsViewState()
    }
}