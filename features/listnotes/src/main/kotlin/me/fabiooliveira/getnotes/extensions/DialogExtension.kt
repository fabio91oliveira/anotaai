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