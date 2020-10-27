package me.fabiooliveira.getnotes.listnotes.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.listnotes.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.listnotes.domain.analytics.ListNotesAnalytics
import me.fabiooliveira.getnotes.listnotes.domain.usecase.ChangeDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.CheckHasToShowOnBoardingUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.CheckIfVersionIsOutDated
import me.fabiooliveira.getnotes.listnotes.domain.usecase.FetchUpdateConfigUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetDarkModePreferencesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetPastListNotesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.GetRecentListNotesUseCase
import me.fabiooliveira.getnotes.listnotes.domain.usecase.MountNoteItemsUseCase
import me.fabiooliveira.getnotes.listnotes.presentation.action.ListNotesAction
import me.fabiooliveira.getnotes.listnotes.presentation.viewstate.ListNotesPopUpViewState
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
        private val checkHasToShowOnBoardingUseCase: CheckHasToShowOnBoardingUseCase,
        private val fetchUpdateConfigUseCase: FetchUpdateConfigUseCase,
        private val checkIfVersionIsOutDated: CheckIfVersionIsOutDated,
        private val listNotesAnalytics: ListNotesAnalytics,
) : ViewModel() {

    private val _listNotesViewState by lazy { MutableLiveData<ListNotesViewState>() }
    val listNotesViewState: LiveData<ListNotesViewState> = _listNotesViewState

    private val _listNotesPopUpViewState by lazy { MutableLiveData<ListNotesPopUpViewState>() }
    val listNotesPopUpViewState: LiveData<ListNotesPopUpViewState> = _listNotesPopUpViewState

    private val _recentListNotesViewState by lazy { MutableLiveData<RecentListNotesViewState>() }
    val recentListNotesViewState: LiveData<RecentListNotesViewState> = _recentListNotesViewState

    private val _pastListNotesViewState by lazy { MutableLiveData<PastListNotesViewState>() }
    val pastListNotesViewState: LiveData<PastListNotesViewState> = _pastListNotesViewState

    private val _listNotesAction by lazy { MutableLiveData<ListNotesAction>() }
    val listNotesAction: LiveData<ListNotesAction> = _listNotesAction

    init {
        initStates()
        checkIfNeedsToShowPopUpUpdateMessage()
        listNotesAnalytics.trackScreenListNotes()
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

    fun showOnBoardingPopUp() {
        viewModelScope.launch {
            checkHasToShowOnBoardingUseCase()
                    .flowOn(Dispatchers.Main)
                    .catch {
                        handleError(it)
                    }
                    .filter { it }
                    .collect { _ ->
                        ListNotesAction.ShowOnBoarding.sendAction()
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

    fun goToEditNote(noteItem: NoteItem) {
        ListNotesAction.GoToEditNote(noteItem).sendAction()
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

    fun trackRecentTab() {
        listNotesAnalytics.trackTabRecentNotes()
    }

    fun trackPastTab() {
        listNotesAnalytics.trackTabPastNotes()
    }

    fun trackUpdateOkClicked() {
        listNotesAnalytics.trackUpdatePopUpOkClicked()
    }

    fun trackUpdateCancelClicked() {
        listNotesAnalytics.trackUpdatePopUpCancelClicked()
    }

    fun trackChangeThemeMode(isDarkModeEnabled: Boolean) {
        listNotesAnalytics.trackChangeThemeMode(isDarkModeEnabled)
    }

    private fun checkIfNeedsToShowPopUpUpdateMessage() {
        viewModelScope.launch {
            fetchUpdateConfigUseCase()
                    .flowOn(Dispatchers.IO)
                    .map {
                        checkIfVersionIsOutDated(it)
                    }
                    .catch {
                        handleError(it)
                    }
                    .filter { (hasToShowMessage, _) ->
                        hasToShowMessage
                    }
                    .map { (_, updateConfig) ->
                        updateConfig.forceUpdate
                    }
                    .collect { forceUpdate ->
                        showPopUpUpdateMessage(forceUpdate)
                        listNotesAnalytics.trackUpdatePopUpViewed(forceUpdate)
                    }
        }
    }

    private fun showPopUpUpdateMessage(forceUpdate: Boolean) {
        setListNotesPopUpViewState {
            it.copy(
                    dialog = ListNotesPopUpViewState.Dialog.ShowUpdateDialog(
                            titleRes = if (forceUpdate) R.string.list_notes_feature_force_update_message_title else R.string.list_notes_feature_update_message_title,
                            descriptionRes = if (forceUpdate) R.string.list_notes_feature_force_update_message_description else R.string.list_notes_feature_update_message_description,
                            okButtonTextRes = if (forceUpdate) R.string.list_notes_feature_force_update_message_ok_button_text else R.string.list_notes_feature_update_message_ok_button_text,
                            cancelButtonTextRes = if (forceUpdate) R.string.list_notes_feature_update_message_cancel_button_text else R.string.list_notes_feature_update_message_cancel_button_text,
                            isForceUpdate = forceUpdate
                    )
            )
        }
    }

    fun hideDialogs() {
        setListNotesPopUpViewState {
            it.copy(
                    dialog = ListNotesPopUpViewState.Dialog.NoDialog)
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

    private fun setListNotesPopUpViewState(state: (ListNotesPopUpViewState) -> ListNotesPopUpViewState) {
        _listNotesPopUpViewState.value?.also {
            _listNotesPopUpViewState.value = state(it)
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
        _listNotesPopUpViewState.value = ListNotesPopUpViewState.init()
    }
}