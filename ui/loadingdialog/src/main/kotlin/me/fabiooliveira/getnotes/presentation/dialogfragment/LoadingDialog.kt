package me.fabiooliveira.getnotes.presentation.dialogfragment

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.loading_dialog_dialog_fragment.*
import ui.loadingdialog.R

class LoadingDialog(
    context: Context?,
    @StringRes private val resTitle: Int,
    @StringRes private val resDescription: Int
) : AlertDialog(context ?: throw IllegalArgumentException()) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_dialog_dialog_fragment)
        setupTexts()
    }

    private fun setupTexts() {
        txtTitle.setText(resTitle)
        txtDescription.setText(resDescription)
    }
}