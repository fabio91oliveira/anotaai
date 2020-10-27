package me.fabiooliveira.getnotes.listnotes.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import features.listnotes.R
import kotlinx.android.synthetic.main.list_notes_feature_fragment_recent_list_notes.*
import me.fabiooliveira.getnotes.listnotes.presentation.adapter.NoteItemsAdapter
import me.fabiooliveira.getnotes.listnotes.presentation.viewmodel.ListNotesViewModel
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class RecentListNotesFragment : Fragment(R.layout.list_notes_feature_fragment_recent_list_notes),
        NoteItemsAdapter.NoteClickListener {

    private val listNotesViewModel: ListNotesViewModel by sharedViewModel()
    private val noteItemsAdapter: NoteItemsAdapter by lazy { NoteItemsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupRecyclerView()
        listNotesViewModel.getRecentNotesList()
    }

    override fun onResume() {
        super.onResume()
        listNotesViewModel.setTabName(R.string.list_notes_feature_tab_recents)
        listNotesViewModel.trackRecentTab()
    }

    override fun onClickNote(noteItem: NoteItem) {
        listNotesViewModel.goToEditNote(noteItem)
    }

    private fun setupObservables() {
        with(listNotesViewModel) {
            recentListNotesViewState.observe(viewLifecycleOwner, Observer {
                addNotes(it.notes)
                showContent(it.isContentVisible)
                showLoading(it.isLoading)
                showEmptyState(it.isEmptyState)
            })
        }
    }

    private fun setupRecyclerView() {
        with(rvRecentNotes) {
            layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
            )
            adapter = noteItemsAdapter
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    if (dy > 0)
//                        listNotesViewModel.hideRecentNotesAddButton()
//                    else if (dy < 0)
//                        listNotesViewModel.showRecentNotesAddButton()
//                }
//            })
        }
    }

    private fun addNotes(noteItemsList: List<NoteItem>?) {
        noteItemsList?.also {
            noteItemsAdapter.clearNotes()
            noteItemsAdapter.addNotes(it)
            noteItemsAdapter.notifyDataSetChanged()
        }
    }

    private fun showContent(isVisible: Boolean) {
        rvRecentNotes.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    private fun showLoading(isVisible: Boolean) {
        loading.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showEmptyState(isVisible: Boolean) {
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        ivNotFound.visibility = visibility
        tvNotFound.visibility = visibility
    }

    companion object {
        fun newInstance() =
                RecentListNotesFragment()
    }
}