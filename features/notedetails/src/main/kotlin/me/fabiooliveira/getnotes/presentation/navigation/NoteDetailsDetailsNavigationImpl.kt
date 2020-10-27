package me.fabiooliveira.getnotes.presentation.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import me.fabiooliveira.getnotes.navigation.NOTE_ITEM_TAG
import me.fabiooliveira.getnotes.navigation.NoteDetailsNavigation
import me.fabiooliveira.getnotes.presentation.activity.NoteDetailsActivity
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem

class NoteDetailsDetailsNavigationImpl : NoteDetailsNavigation {
    override fun navigateToFeature(fragmentActivity: FragmentActivity,
                                   requestCode: Int) {
        fragmentActivity.startActivityForResult(
                Intent(fragmentActivity,
                        NoteDetailsActivity::class.java),
                requestCode
        )
    }

    override fun navigateToFeature(fragmentActivity: FragmentActivity,
                                   bundle: Bundle,
                                   requestCode: Int) {
        val noteItem = bundle.getParcelable(NOTE_ITEM_TAG) as NoteItem?
        fragmentActivity.startActivityForResult(
                Intent(fragmentActivity,
                        NoteDetailsActivity::class.java).apply {
                    putExtra(NOTE_ITEM_TAG, noteItem)
                }, requestCode
        )
    }
}