package me.fabiooliveira.getnotes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import features.notedetails.R
import kotlinx.android.synthetic.main.note_details_feature_bottom_sheet_calendar.*
import me.fabiooliveira.getnotes.extensions.getDateFromString
import java.io.Serializable
import java.util.*

internal class SelectDateFragment : BottomSheetDialogFragment() {

    private val date by lazy {
        arguments?.getString(DATE)
    }

    private val listener by lazy {
        arguments?.getSerializable(LISTENER) as SelectDateListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
                R.layout.note_details_feature_bottom_sheet_calendar,
                container,
                false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDateListener()
        setupClickListener()
        setupDate()
    }

    private fun setupClickListener() {
        tvContinue.setOnClickListener {
            listener.onSelectDate(Date(cvCalendar.date))
            dismiss()
        }
    }

    private fun setupDate() {
        date?.also {
            cvCalendar.date = it.getDateFromString().time
        }
    }

    private fun setupDateListener() {
        cvCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            cvCalendar.date = calendar.timeInMillis
        }
    }

    interface SelectDateListener : Serializable {
        fun onSelectDate(date: Date)
    }

    companion object {
        private const val DATE = "date"
        private const val LISTENER = "listener"

        fun newInstance(
                date: String,
                selectDateListener: SelectDateListener
        ): SelectDateFragment {
            return SelectDateFragment().apply {
                arguments = Bundle().apply {
                    putString(DATE, date)
                    putSerializable(LISTENER, selectDateListener)
                }
            }
        }
    }
}