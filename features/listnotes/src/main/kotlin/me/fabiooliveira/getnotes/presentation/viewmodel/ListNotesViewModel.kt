package me.fabiooliveira.getnotes.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.map
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.domain.usecase.GetListNotesUseCase
import me.fabiooliveira.getnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.presentation.action.RecentListNotesAction
import me.fabiooliveira.getnotes.presentation.viewstate.ListNotesViewState
import me.fabiooliveira.getnotes.presentation.viewstate.RecentListNotesViewState

internal class ListNotesViewModel(
        private val getListNotesUseCase: GetListNotesUseCase,
        private val mountNoteItemsUseCase: MountNoteItemsUseCase
) : ViewModel() {

    private val _recentListNotesAction by lazy { MutableLiveData<RecentListNotesAction>() }
    val recentListNotesAction by lazy { _recentListNotesAction }

    private val _recentListNotesViewState by lazy { MutableLiveData<RecentListNotesViewState>() }
    val recentListNotesViewState by lazy { _recentListNotesViewState }

    private val _listNotesViewState by lazy { MutableLiveData<ListNotesViewState>() }
    val listNotesViewState by lazy { _listNotesViewState }

    init {
        initStates()
    }

    fun getNotesList() {
        viewModelScope.launch {
            setRecentNotesViewState {
                it.copy(isLoading = true,
                        isContentVisible = false)
            }
            Result.of(getListNotesUseCase())
                    .map {
                        mountNoteItemsUseCase(it)
                    }
                    .fold(success = { notes ->
                        setRecentNotesViewState {
                            it.copy(
                                    isLoading = false,
                                    notes = notes,
                                    isContentVisible = true
                            )
                        }
                    }, failure = {
                        val a = ""
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

    fun showRecentNotesAddButton() {
        setRecentNotesViewState {
            it.copy(isAddButtonVisible = true)
        }
    }

    fun hideRecentNotesAddButton() {
        setRecentNotesViewState {
            it.copy(isAddButtonVisible = false)
        }
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

    private fun initStates() {
        _recentListNotesViewState.value = RecentListNotesViewState.init()
        _listNotesViewState.value = ListNotesViewState.init()
    }
}