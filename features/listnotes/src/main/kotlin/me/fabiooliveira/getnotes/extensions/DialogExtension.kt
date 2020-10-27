package me.fabiooliveira.getnotes.extensions

import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import features.listnotes.R
import me.fabiooliveira.getnotes.presentation.dialogfragment.PopupDialog

fun FragmentActivity.openDialog(@StringRes titleRes: Int,
                                @StringRes descriptionRes: Int) {
    PopupDialog.Builder()
            .setTitle(titleRes)
            .setSubtitle(descriptionRes)
            .setupConfirmButtonColor(R.color.color_accent)
            .setupConfirmButton(
                    R.string.all_button_ok,
                    object : PopupDialog.PopupDialogConfirmListener {
                        override fun onClickConfirmButton() {

                        }
                    }
            )
            .build()
            .show(supportFragmentManager, PopupDialog.TAG)
}

fun FragmentActivity.openDialogWithCancelButton(@StringRes titleRes: Int,
                                                @StringRes descriptionRes: Int,
                                                @StringRes okButtonTextRes: Int,
                                                @StringRes cancelButtonTextRes: Int,
                                                blockConfirm: () -> Unit,
                                                blockCancel: () -> Unit,
                                                isCancelable: Boolean = true) {
    PopupDialog.Builder()
            .setTitle(titleRes)
            .setSubtitle(descriptionRes)
            .setupConfirmButtonColor(R.color.color_accent)
            .setupConfirmButton(
                    okButtonTextRes,
                    object : PopupDialog.PopupDialogConfirmListener {
                        override fun onClickConfirmButton() {
                            blockConfirm()
                        }
                    }
            ).apply {
                if (isCancelable) {
                    setupCancelButton(
                            cancelButtonTextRes,
                            object : PopupDialog.PopupDialogCancelListener {
                                override fun onClickCancelButton() {
                                    blockCancel()
                                }
                            }
                    )
                }
            }
            .build()
            .show(supportFragmentManager, PopupDialog.TAG)
}