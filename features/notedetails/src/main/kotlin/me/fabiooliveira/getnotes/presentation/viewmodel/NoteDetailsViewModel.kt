package me.fabiooliveira.getnotes.presentation.viewmodel

import androidx.lifecycle.LiveData
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
import java.util.*

private const val DELAY_ANIMATION_SUCCESS = 1200L

internal class NoteDetailsViewModel(
        private val calendar: Calendar,
        private val publishNoteUseCase: PublishNoteUseCase,
        private val removeNoteUseCase: RemoveNoteUseCase,
        private val validateEmptyFieldsUseCase: ValidateEmptyFieldsUseCase
) : ViewModel() {

    private val _noteDetailsAction by lazy { MutableLiveData<NoteDetailsAction>() }
    val noteDetailsAction: LiveData<NoteDetailsAction> = _noteDetailsAction

    private val _noteDetailsViewState by lazy { MutableLiveData<NoteDetailsViewState>() }
    val noteDetailsViewState: LiveData<NoteDetailsViewState> = _noteDetailsViewState

    init {
        initState()
    }

    fun publishNote(
            idNote: Long? = null,
            titleNote: String,
            descriptionNote: String,
            date: String,
            time: String,
            relevance: RelevanceEnum,
            isReminder: Boolean
    ) {
        viewModelScope.launch {
            validateEmptyFieldsUseCase(
                    titleNote = titleNote,
                    descriptionNote = descriptionNote,
                    date = date,
                    time = time)
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
                                    relevance = relevance,
                                    isReminder = isReminder
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

    fun updateDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        NoteDetailsAction.UpdateDate(calendar).sendAction()
    }

    fun updateTime(hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        NoteDetailsAction.UpdateTime(calendar).sendAction()
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
            relevance: RelevanceEnum,
            isReminder: Boolean
    ) {
        handleLoading()

        publishNoteUseCase(
                idNote = idNote,
                titleNote = titleNote,
                descriptionNote = descriptionNote,
                calendar = calendar,
                relevance = relevance,
                isReminder = isReminder)
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