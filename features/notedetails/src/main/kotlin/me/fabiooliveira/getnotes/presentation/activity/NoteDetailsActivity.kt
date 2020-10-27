package me.fabiooliveira.getnotes.presentation.activity

import android.app.Activity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog
import com.philliphsu.bottomsheetpickers.time.numberpad.NumberPadTimePickerDialog
import features.notedetails.R
import kotlinx.android.synthetic.main.note_details_feature_activity_note_details.*
import me.fabiooliveira.getnotes.alarm.domain.manager.NoteAlarmManager
import me.fabiooliveira.getnotes.extensions.fadeIn
import me.fabiooliveira.getnotes.extensions.getCalendarFromString
import me.fabiooliveira.getnotes.extensions.getDateString
import me.fabiooliveira.getnotes.extensions.getTimeOfTheDay
import me.fabiooliveira.getnotes.extensions.hideKeyboardFrom
import me.fabiooliveira.getnotes.extensions.isDarkMode
import me.fabiooliveira.getnotes.extensions.safeClick
import me.fabiooliveira.getnotes.extensions.whenNull
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem
import me.fabiooliveira.getnotes.listnotes.presentation.vo.RelevanceEnum
import me.fabiooliveira.getnotes.navigation.NOTE_ITEM_TAG
import me.fabiooliveira.getnotes.presentation.action.NoteDetailsAction
import me.fabiooliveira.getnotes.presentation.dialogfragment.LoadingDialog
import me.fabiooliveira.getnotes.presentation.extensions.openDialog
import me.fabiooliveira.getnotes.presentation.viewmodel.NoteDetailsViewModel
import me.fabiooliveira.getnotes.presentation.viewstate.NoteDetailsViewState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.util.*


internal class NoteDetailsActivity : AppCompatActivity(R.layout.note_details_feature_activity_note_details),
        DatePickerDialog.OnDateSetListener, BottomSheetTimePickerDialog.OnTimeSetListener {

    private val progressDialog by lazy {
        LoadingDialog(
                this,
                features.listnotes.R.string.all_please_wait,
                features.listnotes.R.string.all_loading
        )
    }

    private val titleNote: String
        get() = itNoteTitle.text.toString()

    private val descriptionNote: String
        get() = itNoteDescription.text.toString()

    private val dateNote: String
        get() = tvDateDescription.text.toString()

    private val timeNote: String
        get() = tvTimeDescription.text.toString()

    private val isReminder: Boolean
        get() = swReminder.isChecked

    private val noteItem: NoteItem? by lazy { intent.extras?.getParcelable(NOTE_ITEM_TAG) as NoteItem? }
    private val noteDetailsViewModel: NoteDetailsViewModel by viewModel { parametersOf(noteItem?.calendar) }
    private val noteAlarmManager: NoteAlarmManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupObservables()
        setupClickListener()
        setupNoteItem()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        noteItem?.also {
            menuInflater.inflate(R.menu.note_details_feature_menu_more_options, menu)
        }.whenNull {
            menuInflater.inflate(R.menu.note_details_feature_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.iPublishNote -> {
                publishNote()
                true
            }
            R.id.iMoreOptions -> {
                showOptions(findViewById(R.id.iMoreOptions))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDateSet(dialog: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        noteDetailsViewModel.updateDate(year, monthOfYear, dayOfMonth)
    }

    override fun onTimeSet(viewGroup: ViewGroup?, hourOfDay: Int, minute: Int) {
        noteDetailsViewModel.updateTime(hourOfDay, minute)
    }

    private fun showOptions(view: View) {
        PopupMenu(this, view).apply {
            inflate(R.menu.note_details_feature_menu_note_existing)
            show()
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.iSaveNote -> {
                        publishNote()
                    }
                    else -> noteDetailsViewModel.showConfirmationDialogRemoveRecentNote()
                }
                true
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        handleRadioButtonsCheck(view.id)
    }

    private fun handleRadioButtonsCheck(radioId: Int) {
        arrayListOf(rbNormal, rbMedium, rbHigh).forEach {
            it.isChecked = it.id == radioId
        }
    }

    private fun getRelevance(): RelevanceEnum {
        arrayListOf(rbNormal, rbMedium, rbHigh).forEach {
            if (it.isChecked) {
                return when (it.id) {
                    R.id.rbNormal -> RelevanceEnum.NORMAL
                    R.id.rbMedium -> RelevanceEnum.MEDIUM
                    else -> RelevanceEnum.HIGH
                }
            }
        }
        return RelevanceEnum.NORMAL
    }

    private fun setupToolbar() {
        with(toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            setNavigationOnClickListener {
                noteDetailsViewModel.trackButtonCloseClicked()
                onBackPressed()
            }
        }
    }

    private fun setupObservables() {
        with(noteDetailsViewModel) {
            noteDetailsAction.observe(this@NoteDetailsActivity, Observer {
                when (it) {
                    is NoteDetailsAction.Success -> showSuccess()
                    is NoteDetailsAction.CloseScreen -> closeScreen()
                    is NoteDetailsAction.Error -> {

                    }
                    is NoteDetailsAction.UpdateDate -> setDate(it.cal)
                    is NoteDetailsAction.UpdateTime -> setTime(it.cal)
                    is NoteDetailsAction.SetAlarm -> scheduleAlarm(it.noteId, it.noteTitle, it.noteContent, it.cal)
                    is NoteDetailsAction.CancelAlarm -> cancelAlarm(it.noteId)
                }
            })
            noteDetailsViewState.observe(this@NoteDetailsActivity, Observer {
                showLoading(it.isLoading)
                handleDialog(it.dialog)
            })
        }
    }

    private fun setupClickListener() {
        clDate.safeClick({
            openSelectDateBottomSheetDialog()
        })
        clTime.safeClick({
            openSelectTimeDialog()
        })
        clReminder.setOnClickListener {
            swReminder.isChecked = swReminder.isChecked.not()
            noteDetailsViewModel.trackReminderClicked(swReminder.isChecked)
        }
    }

    private fun setupDefaultValue() {
        rbNormal.isChecked = true
    }

    private fun setupNoteItem() {
        noteDetailsViewModel.trackScreenMode(noteItem != null)
        noteItem?.also {
            handleNoteItemFields(it)
        }.whenNull {
            setupDefaultValue()
        }
    }

    private fun handleNoteItemFields(noteItem: NoteItem) {
        itNoteTitle.setText(noteItem.title)
        itNoteDescription.setText(noteItem.description)
        tvDateTitle.text = resources.getString(R.string.note_details_feature_date_filled_hint)
        tvTimeTitle.text = resources.getString(R.string.note_details_feature_time_filled_hint)
        setDate(noteItem.calendar)
        setTime(noteItem.calendar)
        swReminder.isChecked = noteItem.isReminder
        when (noteItem.relevance) {
            RelevanceEnum.NORMAL -> rbNormal.isChecked = true
            RelevanceEnum.MEDIUM -> rbMedium.isChecked = true
            RelevanceEnum.HIGH -> rbHigh.isChecked = true
        }
    }

    private fun publishNote() {
        noteDetailsViewModel.publishNote(
                idNote = noteItem?.id,
                titleNote = titleNote,
                descriptionNote = descriptionNote,
                date = dateNote,
                time = timeNote,
                relevance = getRelevance(),
                isReminder = isReminder
        )
    }

    private fun showSuccess() {
        hideKeyboardFrom(mainContent)
        llSuccess.fadeIn()
    }

    private fun closeScreen() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun scheduleAlarm(noteId: Long,
                              noteTitle: String,
                              noteContent: String,
                              calendar: Calendar) {
        noteAlarmManager.scheduleAlarm(
                noteId = noteId,
                noteTitle = noteTitle,
                noteContent = noteContent,
                calendar = calendar
        )
    }

    private fun cancelAlarm(noteId: Long) {
        noteAlarmManager.cancelAlarm(
                noteId = noteId
        )
    }

    private fun showLoading(hasToShow: Boolean) =
            if (hasToShow) progressDialog.show() else progressDialog.dismiss()

    private fun openSelectDateBottomSheetDialog() {
        val dialog = DatePickerDialog.Builder(this, dateNote.getCalendarFromString().get(Calendar.YEAR),
                dateNote.getCalendarFromString().get(Calendar.MONTH),
                dateNote.getCalendarFromString().get(Calendar.DAY_OF_MONTH))
                .setMinDate(Calendar.getInstance())
                .setThemeDark(isDarkMode())
                .build()
        dialog.show(supportFragmentManager, named<DatePickerDialog>().value)
    }

    private fun openSelectTimeDialog() {
        val dialog = NumberPadTimePickerDialog.Builder(this,
                DateFormat.is24HourFormat(this))
                .setThemeDark(isDarkMode())
                .build()
        dialog.show(supportFragmentManager, named<NumberPadTimePickerDialog>().value)
    }

    private fun setDate(cal: Calendar) {
        tvDateDescription.text = cal.time.getDateString()
        tvDateDescription.visibility = View.VISIBLE
    }

    private fun setTime(calendar: Calendar) {
        tvTimeDescription.text = calendar.time.getTimeOfTheDay(this)
        tvTimeDescription.visibility = View.VISIBLE
    }

    private fun handleDialog(dialogViewState: NoteDetailsViewState.Dialog) {
        when (dialogViewState) {
            is NoteDetailsViewState.Dialog.ConfirmationDialogRemoveNote -> {
                openDialog(titleRes = dialogViewState.titleRes,
                        descriptionRes = dialogViewState.descriptionRes,
                        blockConfirm = {
                            noteDetailsViewModel.hideDialogs()
                            noteDetailsViewModel.removeNote(noteItem?.id)
                            noteDetailsViewModel.trackRemoveButtonClicked()
                        },
                        blockCancel = {
                            noteDetailsViewModel.hideDialogs()
                        })
            }
            is NoteDetailsViewState.Dialog.GenericMessageDialog -> {
                openDialog(
                        titleRes = dialogViewState.titleRes,
                        descriptionRes = dialogViewState.descriptionRes,
                        blockConfirm = {
                            noteDetailsViewModel.hideDialogs()
                        }
                )
            }
            else -> {
            }
        }
    }
}