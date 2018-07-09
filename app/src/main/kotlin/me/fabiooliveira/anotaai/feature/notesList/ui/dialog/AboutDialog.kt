package me.fabiooliveira.anotaai.feature.notesList.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.about_dialog.view.*
import me.fabiooliveira.anotaai.R
import android.content.Intent
import android.net.Uri

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class AboutDialog: BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.about_dialog, container, false)
        view.lnLinkedin.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_linkedin)))
            startActivity(browserIntent)
        }
        view.setOnClickListener {
            this@AboutDialog.dismiss()
        }
        return view
    }
}