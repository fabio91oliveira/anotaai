package me.fabiooliveira.getnotes.searchnotes.presentation.navigation

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import me.fabiooliveira.getnotes.navigation.SearchNotesNavigation
import me.fabiooliveira.getnotes.searchnotes.presentation.activity.SearchNotesActivity

class SearchNotesNavigationImpl : SearchNotesNavigation {
    override fun navigateToFeature(fragmentActivity: FragmentActivity, requestCode: Int) {
        fragmentActivity.startActivityForResult(Intent(fragmentActivity, SearchNotesActivity::class.java), requestCode)
    }
}