package me.fabiooliveira.getnotes.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import features.notedetails.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.domain.usecase.PublishNoteUseCase
import me.fabiooliveira.getnotes.domain.usecase.RemoveNoteUseCase
import me.fabiooliveira.getnotes.domain.usecase.ValidateEmptyFieldsUseCase
import me.fabiooliveira.getnotes.listnotes.presentation.vo.RelevanceEnum
import me.fabiooliveira.getnotes.presentation.action.NoteDetailsAction
import me.fabiooliveira.getnotes.presentation.viewstate.NoteDetailsViewState

private const val DELAY_ANIMATION_SUCCESS = 1200L

internal class NoteDetailsViewModel(
        private val publishNoteUseCase: PublishNoteUseCase,
        private val removeNoteUseCase: RemoveNoteUseCase,
        private val validateEmptyFieldsUseCase: ValidateEmptyFieldsUseCase
) : ViewModel() {

    private val _noteDetailsAction by lazy { MutableLiveData<NoteDetailsAction>() }
    val noteDetailsAction by lazy { _noteDetailsAction }

    private val _noteDetailsViewState by lazy { MutableLiveData<NoteDetailsViewState>() }
    val noteDetailsViewState by lazy { _noteDetailsViewState }

    init {
        initState()
    }

    fun publishNote(
            idNote: Long? = null,
            titleNote: String,
            descriptionNote: String,
            date: String,
            relevance: RelevanceEnum
    ) {
        viewModelScope.launch {
            validateEmptyFieldsUseCase(
                    titleNote = titleNote,
                    descriptionNote = descriptionNote,
                    date = date)
                    .flowOn(Dispatchers.Default)
                    .catch {
                        NoteDetailsAction.Error.sendAction()
                    }
                    .collect { isValid ->
                        if (isValid) {
                            saveNote(
                                    idNote = idNote,
                                    titleNote = titleNote,
                                    descriptionNote = descriptionNote,
                                    date = date,
                                    relevance = relevance
                            )
                        } else {
                            showEmptyFieldsDialog()
                        }
                    }
        }
    }

    fun removeNote(noteId: Long?) {
        viewModelScope.launch {
            noteId?.also {
                handleLoading()
                removeNoteUseCase(noteId)
                        .flowOn(Dispatchers.IO)
                        .catch {
                            handleLoading()
                            NoteDetailsAction.Error.sendAction()
                        }
                        .collect {
                            handleLoading()
                            handleSuccess()
                        }
            }
        }
    }

    fun showConfirmationDialogRemoveRecentNote() =
            setViewState {
                it.copy(
                        dialog = NoteDetailsViewState.Dialog.ConfirmationDialogRemoveNote(
                                R.string.note_details_feature_remove_note_title,
                                R.string.note_details_feature_remove_note_message
                        )
                )
            }

    fun hideDialogs() =
            setViewState {
                it.copy(dialog = NoteDetailsViewState.Dialog.NoDialog)
            }

    private fun showEmptyFieldsDialog() =
            setViewState {
                it.copy(
                        dialog = NoteDetailsViewState.Dialog.EmptyFieldsDialog(
                                R.string.note_details_feature_empty_fields_note_title,
                                R.string.note_details_feature_empty_fields_note_message
                        )
                )
            }

    private suspend fun saveNote(
            idNote: Long? = null,
            titleNote: String,
            descriptionNote: String,
            date: String,
            relevance: RelevanceEnum
    ) {
        handleLoading()

        publishNoteUseCase(
                idNote = idNote,
                titleNote = titleNote,
                descriptionNote = descriptionNote,
                date = date,
                relevance = relevance)
                .flowOn(Dispatchers.IO)
                .catch {
                    handleLoading()
                    NoteDetailsAction.Error.sendAction()
                }
                .collect {
                    handleLoading()
                    handleSuccess()
                }
    }

    private suspend fun handleSuccess() {
        NoteDetailsAction.Success.sendAction()
        delay(DELAY_ANIMATION_SUCCESS)
        NoteDetailsAction.CloseScreen.sendAction()
    }

    private fun handleLoading() {
        setViewState {
            it.copy(isLoading = it.isLoading.not())
        }
    }

    private fun NoteDetailsAction.sendAction() {
        _noteDetailsAction.value = this
    }

    private fun setViewState(state: (NoteDetailsViewState) -> NoteDetailsViewState) {
        _noteDetailsViewState.value?.also {
            _noteDetailsViewState.value = state(it)
        }
    }

    private fun initState() {
        _noteDetailsViewState.value = NoteDetailsViewState.init()
    }
}