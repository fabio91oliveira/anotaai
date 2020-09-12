//package me.fabiooliveira.getnotes.legacy.util
//
//import android.content.Context
//import android.content.DialogInterface
//import androidx.appcompat.app.AlertDialog
//import me.fabiooliveira.getnotes.R
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//object DialogUtil {
//    fun showAlertDialog(context: Context, message: String) {
//        val builder = AlertDialog.Builder(context)
//        builder.setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton("OK") { dialog, _ ->
//                    dialog.dismiss()
//                }
//        val alert = builder.create()
//        alert.show()
//    }
//
//    fun showAlertDialog(context: Context, message: String, listener: DialogInterface.OnClickListener) {
//        val builder = AlertDialog.Builder(context)
//        builder.setMessage(message)
//                .setCancelable(false)
//                .setNegativeButton(context.resources.getText(R.string.option_cancel)) { dialogInterface, _ ->
//                    dialogInterface.dismiss()
//                }
//                .setPositiveButton(context.resources.getText(R.string.option_ok), listener)
//        val alert = builder.create()
//        alert.show()
//    }
//}