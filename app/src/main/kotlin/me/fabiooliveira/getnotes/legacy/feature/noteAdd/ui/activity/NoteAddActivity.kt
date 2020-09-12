//package me.fabiooliveira.getnotes.legacy.feature.noteAdd.ui.activity
//
//import android.app.DatePickerDialog
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProviders
//import android.content.Context
//import android.content.DialogInterface
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.note_add_activity.*
//import me.fabiooliveira.getnotes.R
//import me.fabiooliveira.getnotes.legacy.base.MainApplication
//import me.fabiooliveira.getnotes.legacy.feature.noteAdd.viewModel.NoteAddViewModel
//import me.fabiooliveira.getnotes.legacy.util.DateUtil
//import javax.inject.Inject
//import android.view.inputmethod.InputMethodManager
//import me.fabiooliveira.getnotes.legacy.enum.StatusEnum
//import androidx.core.content.ContextCompat
//import com.xw.repo.BubbleSeekBar
//import me.fabiooliveira.getnotes.legacy.model.entity.Note
//import me.fabiooliveira.getnotes.legacy.util.ActivityStatusConstants
//import me.fabiooliveira.getnotes.legacy.util.DialogUtil
//import me.fabiooliveira.getnotes.legacy.util.ViewUtil
//import java.util.*
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//class NoteAddActivity: AppCompatActivity() {
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory
//    private lateinit var datePickerDialog: DatePickerDialog
//    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(NoteAddViewModel::class.java) }
//
//    private lateinit var note: Note
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.note_add_activity)
//        initDagger()
//        ViewUtil.changeStatusBarColor(this, R.color.colorPrimary, false)
//        initDatePicker()
//        initListeners()
//        initLiveDataListeners()
//        initNoteToEdit()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        viewModel.onDestroy()
//    }
//
//    private fun initDagger() {
//        (this.application as MainApplication)
//                .getMainApplicationComponent()
//                .inject(this)
//    }
//
//    private fun initDatePicker() {
//        val calendar = DateUtil.getCalendar()
//        datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//            etDate.setText(DateUtil.formatDate(dayOfMonth, month, year))
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
//    }
//
//    private fun saveNote() {
//        when(!viewModel.isEmpty(etDate.text.toString())) {
//            true-> {
//                val date = DateUtil.convertFormatDate(etDate.text.toString())
//
//                note.title = etTitle.text.toString()
//                note.contentDescription = etDescription.text.toString()
//                note.date = date
//                note.relevance = sbRelevance.progress
//
//                when (viewModel.isDateBeforeToday(date)) {
//                    true -> {
//                        DialogUtil.showAlertDialog(this, this.resources.getString(R.string.note_add_error_date), DialogInterface.OnClickListener { dialogInterface, _ ->
//                            viewModel.addNote(note)
//                            dialogInterface.dismiss()})}
//                    false -> viewModel.addNote(note)
//                }
//            }
//            false -> DialogUtil.showAlertDialog(this, this.resources.getString(R.string.note_add_error_fields_at_least_date))
//        }
//    }
//
//    private fun hideKeyboard() {
//        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        if (inputMethodManager.isActive) { currentFocus?.let { inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0) } }
//    }
//
//    private fun initListeners() {
//        toolbar.setNavigationOnClickListener { it.let { finish() } }
//        etDate.setOnClickListener {
//            hideKeyboard()
//            datePickerDialog.show()
//        }
//        bvSave.setOnClickListener { saveNote() }
//        sbRelevance.onProgressChangedListener = object : BubbleSeekBar.OnProgressChangedListener {
//            override fun onProgressChanged(progress: Int, progressFloat: Float) { }
//            override fun getProgressOnActionUp(progress: Int, progressFloat: Float) {
//                tvRelevance.setTextColor(ContextCompat.getColor(this@NoteAddActivity, R.color.colorPrimary)) }
//            override fun getProgressOnFinally(progress: Int, progressFloat: Float) { }
//        }
//    }
//
//    private fun initLiveDataListeners() {
//        viewModel.resourceMutableLive.observe(this, Observer {
//            it?.let {when (it.statusEnum) {
//                StatusEnum.ERROR -> setResult(ActivityStatusConstants.ERROR)}}
//            finish()})
//    }
//
//    private fun initNoteToEdit(){
//        if(intent.getSerializableExtra(ActivityStatusConstants.NOTE_EDIT) != null) {
//            note = intent.getSerializableExtra(ActivityStatusConstants.NOTE_EDIT) as Note
//
//            etTitle.setText(note.title)
//            etDescription.setText(note.contentDescription)
//
//            val dateCalendar = DateUtil.getCalendar(note.date)
//            val date = DateUtil.formatDate(dateCalendar.get(Calendar.DAY_OF_MONTH), dateCalendar.get(Calendar.MONTH), dateCalendar.get(Calendar.YEAR))
//            etDate.setText(date)
//            tvRelevance.setTextColor(ContextCompat.getColor(this@NoteAddActivity, R.color.colorPrimary))
//            sbRelevance.setProgress(note.relevance.toFloat())
//        } else {
//            note = Note("", "", Date(), 0)
//        }
//    }
//}