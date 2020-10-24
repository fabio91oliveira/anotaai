package me.fabiooliveira.getnotes.listnotes.presentation.viewstate

import androidx.annotation.StringRes

internal data class ListNotesPopUpViewState(
        val dialog: Dialog = Dialog.NoDialog
) {
    sealed class Dialog {
        object NoDialog : Dialog()
        data class ShowUpdateDialog(
                @StringRes val titleRes: Int,
                @StringRes val descriptionRes: Int,
                @StringRes val okButtonTextRes: Int,
                @StringRes val cancelButtonTextRes: Int,
                val isForceUpdate: Boolean
        ) :
                Dialog()
    }

    companion object {
        fun init() = ListNotesPopUpViewState()
    }
}