package me.fabiooliveira.getnotes.searchnotes.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import features.searchnotes.R
import kotlinx.android.synthetic.main.search_notes_feature_activity_search_notes.*
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem
import me.fabiooliveira.getnotes.navigation.NOTE_REQUEST_CODE
import me.fabiooliveira.getnotes.navigation.NOTE_ITEM_TAG
import me.fabiooliveira.getnotes.navigation.NoteDetailsNavigation
import me.fabiooliveira.getnotes.searchnotes.presentation.action.SearchNotesAction
import me.fabiooliveira.getnotes.searchnotes.presentation.adapter.NoteItemsAdapter
import me.fabiooliveira.getnotes.searchnotes.presentation.viewmodel.SearchNotesViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SearchNotesActivity : AppCompatActivity(R.layout.search_notes_feature_activity_search_notes),
        NoteItemsAdapter.NoteClickListener {

    private val searchNotesViewModel: SearchNotesViewModel by viewModel()
    private val noteDetailsNavigation: NoteDetailsNavigation by inject()
    private val noteItemsAdapter: NoteItemsAdapter by lazy { NoteItemsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupSearchListener()
        setupObservables()
        requestSearchFocus()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == NOTE_REQUEST_CODE) {
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    override fun onClickNote(noteItem: NoteItem) {
        searchNotesViewModel.goToEditNote(noteItem)
    }

    private fun setupToolbar() {
        with(toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun setupRecyclerView() {
        with(rvNotes) {
            layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
            )
            adapter = noteItemsAdapter
        }
    }

    private fun setupSearchListener() {
        etSearch.doAfterTextChanged {
            val name = it.toString()
            searchNotesViewModel.getNotesByText(name)
        }
    }

    private fun setupObservables() {
        with(searchNotesViewModel) {
            searchNotesViewState.observe(this@SearchNotesActivity, {
                addNotes(it.notes)
                handleLoading(it.isLoading)
                handleContent(it.isContentVisible)
                handleEmptyState(it.isEmptyState)
                handleError(it.isError)
            })
            searchNotesAction.observe(this@SearchNotesActivity, {
                when (it) {
                    is SearchNotesAction.GoToEditNote -> openEditNote(it.noteItem)
                }
            })
        }
    }

    private fun requestSearchFocus() {
        etSearch.requestFocus()
    }

    private fun handleLoading(isLoading: Boolean) {
        loading.isVisible = isLoading
    }

    private fun handleContent(isVisible: Boolean) {
        rvNotes.isVisible = isVisible
    }

    private fun handleEmptyState(isVisible: Boolean) {
        ivNotFound.isVisible = isVisible
        tvNotFound.isVisible = isVisible
    }

    private fun handleError(isError: Boolean) {
        tvErrorMessage.isVisible = isError
    }

    private fun addNotes(noteItemsList: List<NoteItem>?) {
        noteItemsList?.also {
            noteItemsAdapter.clearNotes()
            noteItemsAdapter.addNotes(it)
            noteItemsAdapter.notifyDataSetChanged()
        }
    }

    private fun openEditNote(noteItem: NoteItem) {
        val bundle = Bundle().apply {
            putParcelable(NOTE_ITEM_TAG, noteItem)
        }
        noteDetailsNavigation.navigateToFeature(
                this,
                bundle,
                NOTE_REQUEST_CODE)
    }
}