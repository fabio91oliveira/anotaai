package me.fabiooliveira.getnotes.navigation

import androidx.fragment.app.FragmentActivity

const val SEARCH_NOTES_REQUEST_CODE = 11

interface SearchNotesNavigation {
    fun navigateToFeature(fragmentActivity: FragmentActivity, requestCode: Int)
}