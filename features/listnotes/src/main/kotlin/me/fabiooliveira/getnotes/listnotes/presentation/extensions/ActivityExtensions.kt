package me.fabiooliveira.getnotes.listnotes.presentation.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import features.listnotes.R

fun Activity.openAppInPlayStore() {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_store_link))))
    } catch (exception: android.content.ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_web_store_link))))
    }
}