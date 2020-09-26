package me.fabiooliveira.getnotes.searchnotes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.listnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem
import me.fabiooliveira.getnotes.searchnotes.domain.usecase.GetNotesByTextUseCase
import me.fabiooliveira.getnotes.searchnotes.presentation.action.SearchNotesAction
import me.fabiooliveira.getnotes.searchnotes.presentation.viewstate.SearchNotesViewState
import timber.log.Timber

internal class SearchNotesViewModel(
        private val getNotesByTextUseCase: GetNotesByTextUseCase,
        private val mountNoteItemsUseCase: MountNoteItemsUseCase
) : ViewModel() {

    private val _searchNotesViewState by lazy { MutableLiveData<SearchNotesViewState>() }
    val searchNotesViewState: LiveData<SearchNotesViewState> = _searchNotesViewState

    private val _searchNotesAction by lazy { MutableLiveData<SearchNotesAction>() }
    val searchNotesAction: LiveData<SearchNotesAction> = _searchNotesAction

    init {
        initState()
    }

    fun getNotesByText(text: String) {
        viewModelScope.launch {
            showLoading()
            if (text.isEmpty()) {
                handleEmptyState()
            } else {
                getNotesByTextUseCase(text)
                        .map {
                            mountNoteItemsUseCase(it)
                        }
                        .flowOn(Dispatchers.IO)
                        .catch {
                            handleError(it)
                        }
                        .collect { notes ->
                            handleSuccess(notes)
                        }
            }
        }
    }

    fun goToEditNote(noteItem: NoteItem) {
        SearchNotesAction.GoToEditNote(noteItem).sendAction()
    }

    private fun handleSuccess(notes: List<NoteItem>) {
        setSearchNotesViewState {
            it.copy(
                    isLoading = false,
                    isEmptyState = notes.isEmpty(),
                    isContentVisible = notes.isNotEmpty(),
                    notes = notes
            )
        }
    }

    private fun handleError(throwable: Throwable) {
        Timber.e(throwable)
        setSearchNotesViewState {
            it.copy(isLoading = false,
                    isEmptyState = false,
                    isError = true)
        }
    }

    private fun handleEmptyState() {
        setSearchNotesViewState {
            it.copy(isLoading = false,
                    isEmptyState = true,
                    isContentVisible = false,
                    isError = false)
        }
    }

    private fun showLoading() {
        setSearchNotesViewState {
            it.copy(isLoading = true,
                    isEmptyState = false,
                    isContentVisible = false)
        }
    }

    private fun setSearchNotesViewState(state: (SearchNotesViewState) -> SearchNotesViewState) {
        _searchNotesViewState.value?.also {
            _searchNotesViewState.value = state(it)
        }
    }

    private fun SearchNotesAction.sendAction() {
        _searchNotesAction.value = this
    }

    private fun initState() {
        _searchNotesViewState.value = SearchNotesViewState.init()
    }
}