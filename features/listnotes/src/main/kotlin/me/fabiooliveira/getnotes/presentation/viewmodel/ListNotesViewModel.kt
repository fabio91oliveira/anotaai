package me.fabiooliveira.getnotes.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.map
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.domain.usecase.impl.GetListNotesUseCase
import me.fabiooliveira.getnotes.presentation.action.RecentListNotesAction
import me.fabiooliveira.getnotes.presentation.viewstate.RecentListNotesViewState

internal class ListNotesViewModel(
        private val getListNotesUseCase: GetListNotesUseCase,
        private val mountNoteItemsUseCase: MountNoteItemsUseCase
) : ViewModel() {

    private val _recentListNotesAction by lazy { MutableLiveData<RecentListNotesAction>() }
    val recentListNotesAction by lazy { _recentListNotesAction }

    private val _recentListNotesViewState by lazy { MutableLiveData<RecentListNotesViewState>() }
    val recentListNotesViewState by lazy { _recentListNotesViewState }

    init {
        initState()
    }

    fun getNotesList() {
        viewModelScope.launch {
            setViewState {
                it.copy(isLoading = true,
                        isContentVisible = false)
            }
            Result.of(getListNotesUseCase())
                    .map {
                        mountNoteItemsUseCase(it)
                    }
                    .fold(success = { notes ->
                        setViewState {
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

    fun showRecentNotesAddButton() {
        setViewState {
            it.copy(isAddButtonVisible = true)
        }
    }

    fun hideRecentNotesAddButton() {
        setViewState {
            it.copy(isAddButtonVisible = false)
        }
    }

    private fun setViewState(state: (RecentListNotesViewState) -> RecentListNotesViewState) {
        _recentListNotesViewState.value?.also {
            _recentListNotesViewState.value = state(it)
        }
    }

    private fun initState() {
        _recentListNotesViewState.value = RecentListNotesViewState.init()
    }
}