package me.fabiooliveira.getnotes.listnotes.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.listnotes.domain.usecase.ChangeDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.CheckHasToShowOnBoardingUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetPastListNotesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetRecentListNotesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.listnotes.presentation.action.ListNotesAction
import me.fabiooliveira.getnotes.listnotes.presentation.viewstate.ListNotesViewState
import me.fabiooliveira.getnotes.listnotes.presentation.viewstate.PastListNotesViewState
import me.fabiooliveira.getnotes.listnotes.presentation.viewstate.RecentListNotesViewState
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem
import timber.log.Timber

internal class ListNotesViewModel(
        private val getRecentListNotesUseCase: GetRecentListNotesUseCase,
        private val getPastListNotesUseCase: GetPastListNotesUseCase,
        private val mountNoteItemsUseCase: MountNoteItemsUseCase,
        private val getDarkModePreferencesUseCase: GetDarkModePreferencesUseCase,
        private val changeDarkModePreferencesUseCase: ChangeDarkModePreferencesUseCase,
        private val checkHasToShowOnBoardingUseCase: CheckHasToShowOnBoardingUseCase
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

    fun setColorScheme() {
        viewModelScope.launch {
            getDarkModePreferencesUseCase()
                    .flowOn(Dispatchers.Main)
                    .catch {
                        ListNotesAction.SetLightMode.sendAction()
                        handleError(it)
                    }
                    .collect { isDarkMode ->
                        handleDarkMode(isDarkMode)
                    }
        }
    }

    fun checkIfShowsOnBoarding() {
        viewModelScope.launch {
            checkHasToShowOnBoardingUseCase()
                    .flowOn(Dispatchers.Main)
                    .catch {
                        handleError(it)
                    }
                    .filter { it }
                    .collect { hasToShowOnBoarding ->
                        if (hasToShowOnBoarding) ListNotesAction.ShowOnBoarding.sendAction()
                    }
        }
    }

    fun getRecentNotesList() {
        viewModelScope.launch {
            handleRecentLoadingState()
            getRecentListNotesUseCase()
                    .map {
                        mountNoteItemsUseCase(it)
                    }
                    .flowOn(Dispatchers.IO)
                    .catch {
                        handleError(it)
                    }
                    .collect { notes ->
                        handleRecentSuccessState(notes)
                    }
        }
    }

    fun getPastNotesList() {
        viewModelScope.launch {
            handlePastLoadingState()
            getPastListNotesUseCase()
                    .map {
                        mountNoteItemsUseCase(it)
                    }
                    .flowOn(Dispatchers.IO)
                    .catch {
                        handleError(it)
                    }
                    .collect { notes ->
                        handlePastSuccessState(notes)
                    }
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

    fun switchDarkMode(isDarkModeEnabled: Boolean) {
        viewModelScope.launch {
            changeDarkModePreferencesUseCase(isDarkModeEnabled.not())
                    .flowOn(Dispatchers.Main)
                    .catch {
                        handleError(it)
                    }
                    .collect {
                        handleDarkMode(isDarkModeEnabled.not())
                    }
        }
    }

    private fun handleRecentLoadingState() {
        setRecentNotesViewState {
            it.copy(isLoading = true,
                    isContentVisible = false,
                    isEmptyState = false)
        }
    }

    private fun handleRecentSuccessState(notes: List<NoteItem>) {
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
    }

    private fun handlePastLoadingState() {
        setPastNotesViewState {
            it.copy(isLoading = true,
                    isContentVisible = false,
                    isEmptyState = false)
        }
    }

    private fun handlePastSuccessState(notes: List<NoteItem>) {
        setPastNotesViewState {
            it.copy(
                    isLoading = false,
                    notes = notes,
                    isContentVisible = true,
                    isEmptyState = notes.isEmpty()
            )
        }
    }

    private fun handleError(throwable: Throwable) {
        Timber.e(throwable)
    }

    private fun handleDarkMode(isDarkModeEnabled: Boolean) {
        when (isDarkModeEnabled) {
            true -> ListNotesAction.SetDarkMode.sendAction()
            else -> ListNotesAction.SetLightMode.sendAction()
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