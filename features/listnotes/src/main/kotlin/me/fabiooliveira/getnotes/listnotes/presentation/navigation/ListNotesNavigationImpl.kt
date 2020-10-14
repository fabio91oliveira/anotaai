package me.fabiooliveira.getnotes.listnotes.presentation.navigation

import android.content.Context
import android.content.Intent
import me.fabiooliveira.getnotes.navigation.ListNotesNavigation
import me.fabiooliveira.getnotes.listnotes.presentation.activity.ListNotesActivity

class ListNotesNavigationImpl : ListNotesNavigation {
    override fun navigateToFeature(context: Context) {
        context.startActivity(Intent(context, ListNotesActivity::class.java))
    }

    override fun getIntent(context: Context): Intent {
        return Intent(context, ListNotesActivity::class.java)
    }
}