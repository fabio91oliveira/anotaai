package me.fabiooliveira.anotaai.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

object DialogUtil {
    fun showAlertDialog(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", { dialog, _ ->
                    dialog.dismiss()
                })
        val alert = builder.create()
        alert.show()
    }
}