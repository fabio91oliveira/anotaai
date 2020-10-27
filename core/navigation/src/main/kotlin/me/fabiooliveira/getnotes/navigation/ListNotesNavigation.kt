package me.fabiooliveira.getnotes.navigation

import android.content.Context
import android.content.Intent

interface ListNotesNavigation {
    fun navigateToFeature(context: Context)
    fun getIntent(context: Context): Intent
}