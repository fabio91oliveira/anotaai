package me.fabiooliveira.getnotes.presentation.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import features.notedetails.R
import kotlinx.android.synthetic.main.note_details_feature_activity_note_details.*
import me.fabiooliveira.getnotes.extensions.fadeIn
import me.fabiooliveira.getnotes.extensions.getDateString
import me.fabiooliveira.getnotes.extensions.hideKeyboardFrom
import me.fabiooliveira.getnotes.extensions.whenNull
import me.fabiooliveira.getnotes.navigation.NOTE_ITEM_TAG
import me.fabiooliveira.getnotes.presentation.action.NoteDetailsAction
import me.fabiooliveira.getnotes.presentation.dialogfragment.LoadingDialog
import me.fabiooliveira.getnotes.presentation.extensions.openDialog
import me.fabiooliveira.getnotes.presentation.fragment.SelectDateFragment
import me.fabiooliveira.getnotes.presentation.viewmodel.NoteDetailsViewModel
import me.fabiooliveira.getnotes.presentation.viewstate.NoteDetailsViewState
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem
import me.fabiooliveira.getnotes.listnotes.presentation.vo.RelevanceEnum
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

internal class NoteDetailsActivity : AppCompatActivity(R.layout.note_details_feature_activity_note_details) {

    private val progressDialog by lazy {
        LoadingDialog(
                this,
                features.listnotes.R.string.all_please_wait,
                features.listnotes.R.string.all_loading
        )
    }

    private val noteDetailsViewModel: NoteDetailsViewModel by viewModel()

    private val titleNote: String
        get() = itNoteTitle.text.toString()

    private val descriptionNote: String
        get() = itNoteDescription.text.toString()

    private val dateNote: String
        get() = etNoteDate.text.toString()

    private val noteItem: NoteItem? by lazy { intent.extras?.getParcelable(NOTE_ITEM_TAG) as NoteItem? }

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
            setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun setupObservables() {
        with(noteDetailsViewModel) {
            noteDetailsAction.observe(this@NoteDetailsActivity, Observer {
                when (it) {
                    is NoteDetailsAction.Success -> {
                        showSuccess()
                    }
                    is NoteDetailsAction.CloseScreen -> {
                        closeScreen()
                    }
                    is NoteDetailsAction.Error -> {

                    }
                }
            })
            noteDetailsViewState.observe(this@NoteDetailsActivity, Observer {
                showLoading(it.isLoading)
                handleDialog(it.dialog)
            })
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupClickListener() {
        etNoteDate.setOnClickListener {
            openSelectDateBottomSheetDialog()
        }
        etNoteDate.setOnTouchListener { _, _ ->
           false
        }
    }

    private fun setupDefaultValue() {
        rbNormal.isChecked = true
    }

    private fun setupNoteItem() {
        noteItem?.also {
            itNoteTitle.setText(it.title)
            itNoteDescription.setText(it.description)
            etNoteDate.setText(it.date)
            when (it.relevance) {
                RelevanceEnum.NORMAL -> rbNormal.isChecked = true
                RelevanceEnum.MEDIUM -> rbMedium.isChecked = true
                RelevanceEnum.HIGH -> rbHigh.isChecked = true
            }
        }.whenNull {
            setupDefaultValue()
        }
    }

    private fun publishNote() {
        noteDetailsViewModel.publishNote(
                idNote = noteItem?.id,
                titleNote = titleNote,
                descriptionNote = descriptionNote,
                date = dateNote,
                relevance = getRelevance()
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

    private fun showLoading(hasToShow: Boolean) =
            if (hasToShow) progressDialog.show() else progressDialog.dismiss()

    private fun openSelectDateBottomSheetDialog() {
        val selectDate = SelectDateFragment.newInstance(dateNote, object : SelectDateFragment.SelectDateListener {
            override fun onSelectDate(date: Date) {
                etNoteDate.setText(date.getDateString())
            }
        })
        selectDate.show(supportFragmentManager,
                SelectDateFragment::class.java.name)
    }

    private fun handleDialog(dialogViewState: NoteDetailsViewState.Dialog) {
        when (dialogViewState) {
            is NoteDetailsViewState.Dialog.ConfirmationDialogRemoveNote -> {
                openDialog(titleRes = dialogViewState.titleRes,
                        descriptionRes = dialogViewState.descriptionRes,
                        blockConfirm = {
                            noteDetailsViewModel.hideDialogs()
                            noteDetailsViewModel.removeNote(noteItem?.id)
                        },
                        blockCancel = {
                            noteDetailsViewModel.hideDialogs()
                        })
            }
            is NoteDetailsViewState.Dialog.EmptyFieldsDialog -> {
                openDialog(
                        titleRes = dialogViewState.titleRes,
                        descriptionRes = dialogViewState.descriptionRes,
                        blockConfirm = {
                            noteDetailsViewModel.hideDialogs()
                        }
                )
            }
        }
    }
}