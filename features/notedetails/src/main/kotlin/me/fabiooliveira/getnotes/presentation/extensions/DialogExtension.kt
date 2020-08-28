package me.fabiooliveira.getnotes.presentation.extensions

import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import features.listnotes.R
import me.fabiooliveira.getnotes.presentation.dialogfragment.PopupDialog

fun FragmentActivity.openDialog(@StringRes titleRes: Int,
                                @StringRes descriptionRes: Int,
                                blockConfirm: () -> Unit,
                                blockCancel: () -> Unit) {
    PopupDialog.Builder()
            .setTitle(titleRes)
            .setSubtitle(descriptionRes)
            .setupConfirmButtonColor(R.color.color_accent)
            .setupConfirmButton(
                    R.string.all_button_yes,
                    object : PopupDialog.PopupDialogConfirmListener {
                        override fun onClickConfirmButton() {
                            blockConfirm()
                        }
                    }
            )
            .setupCancelButton(
                    R.string.all_button_no,
                    object : PopupDialog.PopupDialogCancelListener {
                        override fun onClickCancelButton() {
                            blockCancel()
                        }
                    }
            )
            .build()
            .show(supportFragmentManager, PopupDialog.TAG)
}

fun FragmentActivity.openDialog(@StringRes titleRes: Int,
                                @StringRes descriptionRes: Int,
                                blockConfirm: () -> Unit) {
    PopupDialog.Builder()
            .setTitle(titleRes)
            .setSubtitle(descriptionRes)
            .setupConfirmButtonColor(R.color.color_accent)
            .setupConfirmButton(
                    R.string.all_button_ok,
                    object : PopupDialog.PopupDialogConfirmListener {
                        override fun onClickConfirmButton() {
                            blockConfirm()
                        }
                    }
            )
            .build()
            .show(supportFragmentManager, PopupDialog.TAG)
}