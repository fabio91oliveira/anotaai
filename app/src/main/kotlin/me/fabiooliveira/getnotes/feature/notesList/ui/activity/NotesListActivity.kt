package me.fabiooliveira.getnotes.feature.notesList.ui.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import android.view.View
import android.widget.ExpandableListView
import kotlinx.android.synthetic.main.notes_list_activity.*
import me.fabiooliveira.getnotes.R
import me.fabiooliveira.getnotes.base.MainApplication
import me.fabiooliveira.getnotes.model.entity.Note
import me.fabiooliveira.getnotes.feature.notesList.viewModel.NotesListViewModel
import me.fabiooliveira.getnotes.feature.noteAdd.ui.activity.NoteAddActivity
import me.fabiooliveira.getnotes.feature.notesList.listener.NoteInfoInterface
import me.fabiooliveira.getnotes.feature.notesList.ui.adapter.NoteExpandableListAdapter
import javax.inject.Inject
import me.fabiooliveira.getnotes.enum.StatusEnum
import me.fabiooliveira.getnotes.feature.notesList.ui.dialog.AboutDialog
import me.fabiooliveira.getnotes.util.DialogUtil
import me.fabiooliveira.getnotes.util.ActivityStatusConstants
import me.fabiooliveira.getnotes.util.ViewUtil
import me.fabiooliveira.getnotes.feature.notesList.ui.dialog.NoNotesDialog
import me.fabiooliveira.getnotes.feature.onboarding.ui.activity.OnboardingActivity

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class NotesListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory:  ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(NotesListViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_list_activity)
        initDagger()
        initToolbar()
        ViewUtil.changeStatusBarColor(this, R.color.colorPrimary, false)

        if(viewModel.resourceHashMapMutableLiveData.value == null) {
            showLoading()
            viewModel.refreshNoteList()
            viewModel.checkFirstLogin()
        }
        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                showAboutDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            when(resultCode){
                ActivityStatusConstants.SUCCESS -> {
                    showLoading()
                    viewModel.refreshNoteList()
                }
                ActivityStatusConstants.ERROR -> DialogUtil.showAlertDialog(this, this.resources.getString(R.string.note_generic_error))
            }
        }
    }

    private fun initDagger(){
        (this.application as MainApplication)
                .getMainApplicationComponent()
                .inject(this)
    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initListeners() {
        viewModel.resourceHashMapMutableLiveData.observe(this, Observer {
            it?.let {
                it.data?.apply {
                    if(this.isEmpty()) {
                        tvNoNotes.visibility = View.VISIBLE
                        mountNoteList(this)
                        showNoNotesDialog()
                    } else {
                        if(it.statusEnum == StatusEnum.SUCCESS) {
                            mountNoteList(this)
                            expandableNotesList.visibility = View.VISIBLE
                        }
                    }
                }
                hideLoading()
            }
        })
        viewModel.resourceLongMutableLiveData.observe(this, Observer {
            it?.let { if(it.statusEnum == StatusEnum.ERROR) {DialogUtil.showAlertDialog(this, this.resources.getString(R.string.note_generic_error))} }
        })
        viewModel.resourceBooleanMutableLiveData.observe(this, Observer {
            it?.let { if (!it) startActivity(Intent(this, OnboardingActivity::class.java)) }
        })
        viewModel.resourceInsertedLogMutableLiveData.observe(this, Observer {
            it?.let { if(!it) DialogUtil.showAlertDialog(this, this.resources.getString(R.string.note_generic_error)) }
        })
        bvNewNote.setOnClickListener {
            val intent = Intent(this, NoteAddActivity::class.java)
            startActivityForResult(intent, ActivityStatusConstants.STATIC_INT)
        }
    }

    private fun mountNoteList(noteHashMap: LinkedHashMap<String, MutableList<Note>>){
        val expandableListAdapter = NoteExpandableListAdapter(this, viewModel.getNotesTitles(noteHashMap), noteHashMap)
        expandableListAdapter.setNoteInfoInterface(object : NoteInfoInterface {
            override fun onPopupMenuClick(view: View, note: Note) {
                val popupMenu = PopupMenu(this@NotesListActivity, view)
                popupMenu.inflate(R.menu.card_view_menu)
                if(note.isDone) {
                    popupMenu.menu.removeItem(R.id.card_view_menu_done)
                    popupMenu.menu.removeItem(R.id.card_view_menu_edit)
                    if(note.title.isEmpty() || note.contentDescription.isEmpty()) {
                        popupMenu.menu.removeItem(R.id.card_view_menu_undone)
                    }
                } else {
                    popupMenu.menu.removeItem(R.id.card_view_menu_undone)
                    if(note.title.isEmpty() || note.contentDescription.isEmpty()) {
                        popupMenu.menu.removeItem(R.id.card_view_menu_done)
                    }
                }
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.card_view_menu_done, R.id.card_view_menu_undone -> {
                            viewModel.changeStatus(note)
                            true
                        }
                        R.id.card_view_menu_discard -> {
                            viewModel.deleteNote(note)
                            true
                        }
                        R.id.card_view_menu_edit -> {
                            val intent = Intent(this@NotesListActivity, NoteAddActivity::class.java)
                            intent.putExtra("NOTE_EDIT", note)
                            startActivityForResult(intent, ActivityStatusConstants.STATIC_INT)
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
            }
        })
        expandableNotesList.setAdapter(expandableListAdapter)
        expandableNotesList.setOnGroupClickListener { _, _, itemPosition, _ ->
            if(expandableNotesList.isGroupExpanded(itemPosition)) {
                expandableNotesList.collapseGroup(itemPosition)
            } else {
                expandableNotesList.expandGroup(itemPosition)
            }
            true
        }
        expandList(expandableNotesList)
    }

    private fun expandList(mExpandableListView: ExpandableListView) {
        for(i in 0 until mExpandableListView.expandableListAdapter.groupCount) {
            mExpandableListView.expandGroup(i)
        }
    }

    private fun showNoNotesDialog(){
        val dialog = NoNotesDialog()
        dialog.show(supportFragmentManager, "NoNotesDialog")
    }

    private fun showAboutDialog(){
        val dialog = AboutDialog()
        dialog.show(supportFragmentManager, "AboutDialog")
    }

    private fun showLoading(){
        expandableNotesList.visibility = View.GONE
        tvNoNotes.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        progressBar.visibility = View.GONE
    }
}