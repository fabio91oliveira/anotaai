package me.fabiooliveira.getnotes.navigation

import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity

const val CREATE_NOTE_REQUEST_CODE = 12
const val NOTE_ITEM_TAG = "note_item"

interface NoteDetailsNavigation {
    fun navigateToFeature(fragmentActivity: FragmentActivity,
                          requestCode: Int)

    fun navigateToFeature(fragmentActivity: FragmentActivity,
                          bundle: Bundle,
                          requestCode: Int)
}