package me.fabiooliveira.getnotes.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import features.listnotes.R
import kotlinx.android.synthetic.main.list_notes_feature_fragment_past_list_notes.*
import me.fabiooliveira.getnotes.presentation.adapter.NoteItemsAdapter
import me.fabiooliveira.getnotes.presentation.viewmodel.ListNotesViewModel
import me.fabiooliveira.getnotes.presentation.vo.NoteItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class PastListNotesFragment : Fragment(R.layout.list_notes_feature_fragment_past_list_notes) {

    private val listNotesViewModel: ListNotesViewModel by sharedViewModel()

    private val noteItemsAdapter: NoteItemsAdapter by lazy { NoteItemsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupRecyclerView()
    }

    private fun setupObservables() {
        with(listNotesViewModel) {
            recentListNotesViewState.observe(viewLifecycleOwner, Observer {
                addNotes(it.notes)
            })
        }
    }

    private fun setupRecyclerView() {
        with(rvPastNotes) {
            layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
            )
            adapter = noteItemsAdapter
        }
    }

    private fun addNotes(noteItemsList: List<NoteItem>?) {
        noteItemsList?.also {
            noteItemsAdapter.addNotes(it)
            noteItemsAdapter.notifyDataSetChanged()
            rvPastNotes.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() =
                PastListNotesFragment()
    }
}