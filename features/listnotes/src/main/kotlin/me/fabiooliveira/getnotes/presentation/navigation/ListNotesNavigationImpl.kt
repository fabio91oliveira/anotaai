package me.fabiooliveira.getnotes.presentation.navigation

import android.content.Context
import android.content.Intent
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import me.fabiooliveira.getnotes.presentation.activity.ListNotesActivity

class ListNotesNavigationImpl : ListNotesNavigation {
    override fun navigateToFeature(context: Context) {
        context.startActivity(Intent(context, ListNotesActivity::class.java))
    }
}