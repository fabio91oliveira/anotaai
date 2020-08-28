package me.fabiooliveira.getnotes.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.map
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.domain.usecase.GetPastListNotesUseCase
import me.fabiooliveira.getnotes.domain.usecase.GetRecentListNotesUseCase
import me.fabiooliveira.getnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.presentation.action.ListNotesAction
import me.fabiooliveira.getnotes.presentation.viewstate.ListNotesViewState
import me.fabiooliveira.getnotes.presentation.viewstate.PastListNotesViewState
import me.fabiooliveira.getnotes.presentation.viewstate.RecentListNotesViewState
import me.fabiooliveira.getnotes.presentation.vo.NoteItem

internal class ListNotesViewModel(
        private val getRecentListNotesUseCase: GetRecentListNotesUseCase,
        private val getPastListNotesUseCase: GetPastListNotesUseCase,
        private val mountNoteItemsUseCase: MountNoteItemsUseCase
) : ViewModel() {

    private val _listNotesViewState by lazy { MutableLiveData<ListNotesViewState>() }
    val listNotesViewState by lazy { _listNotesViewState }

    private val _recentListNotesViewState by lazy { MutableLiveData<RecentListNotesViewState>() }
    val recentListNotesViewState by lazy { _recentListNotesViewState }

    private val _pastListNotesViewState by lazy { MutableLiveData<PastListNotesViewState>() }
    val pastListNotesViewState by lazy { _pastListNotesViewState }

    private val _listNotesAction by lazy { MutableLiveData<ListNotesAction>() }
    val listNotesAction by lazy { _listNotesAction }

    init {
        initStates()
    }

    fun getRecentNotesList() {
        viewModelScope.launch {
            setRecentNotesViewState {
                it.copy(isLoading = true,
                        isContentVisible = false,
                        isEmptyState = false)
            }
            Result.of(getRecentListNotesUseCase())
                    .map {
                        mountNoteItemsUseCase(it)
                    }
                    .fold(success = { notes ->
                        setRecentNotesViewState {
                            it.copy(
                                    isLoading = false,
                                    notes = notes,
                                    isContentVisible = true,
                                    isEmptyState = notes.isEmpty()
                            )
                        }
                        setListNotesViewState {
                            it.copy(isChangeTabEnabled = true,
                                    isAddButtonVisible = true)
                        }
                    }, failure = {
                    })
        }
    }

    fun getPastNotesList() {
        viewModelScope.launch {
            setPastNotesViewState {
                it.copy(isLoading = true,
                        isContentVisible = false,
                        isEmptyState = false)
            }
            Result.of(getPastListNotesUseCase())
                    .map {
                        mountNoteItemsUseCase(it)
                    }
                    .fold(success = { notes ->
                        setPastNotesViewState {
                            it.copy(
                                    isLoading = false,
                                    notes = notes,
                                    isContentVisible = true,
                                    isEmptyState = notes.isEmpty()
                            )
                        }
                    }, failure = {
                    })
        }
    }

    fun setTabName(@StringRes tabNameRes: Int) {
        setListNotesViewState {
            it.copy(
                    tabSelected = tabNameRes
            )
        }
    }

    fun goToEditNote(noteItem: NoteItem, viewId: Int) {
        ListNotesAction.GoToEditNote(noteItem, viewId).sendAction()
    }

    fun goToCreateNote() {
        ListNotesAction.GoToCreateNote.sendAction()
    }


    private fun setListNotesViewState(state: (ListNotesViewState) -> ListNotesViewState) {
        _listNotesViewState.value?.also {
            _listNotesViewState.value = state(it)
        }
    }

    private fun setRecentNotesViewState(state: (RecentListNotesViewState) -> RecentListNotesViewState) {
        _recentListNotesViewState.value?.also {
            _recentListNotesViewState.value = state(it)
        }
    }

    private fun setPastNotesViewState(state: (PastListNotesViewState) -> PastListNotesViewState) {
        _pastListNotesViewState.value?.also {
            _pastListNotesViewState.value = state(it)
        }
    }

    private fun ListNotesAction.sendAction() {
        _listNotesAction.value = this
    }


    private fun initStates() {
        _recentListNotesViewState.value = RecentListNotesViewState.init()
        _pastListNotesViewState.value = PastListNotesViewState.init()
        _listNotesViewState.value = ListNotesViewState.init()
    }
}