package me.fabiooliveira.getnotes.data.mapper

import me.fabiooliveira.getnotes.base.Mapper
import me.fabiooliveira.getnotes.domain.model.Note
import me.fabiooliveira.getnotes.entity.NoteEntity

internal class NotesPageMapper : Mapper<NoteEntity, Note> {
    override fun map(source: NoteEntity): Note {
        return Note(
                id = source.id,
                title = source.title,
                description = source.contentDescription,
                date = source.date,
                relevance = source.relevance,
                isDone = source.isDone
        )
    }

//    override fun map(source: List<NoteEntity>): List<Note> {
//        val expandableNoteListDetail = linkedMapOf<String, MutableList<Note>>()
//
//        val noteListAux = source.sortedWith(kotlin.Comparator { n1, n2 ->
//            if (getMonthFromDate(n1.date) == getMonthFromDate(n2.date)) {
//                getDayFromDate(n1.date) - getDayFromDate(n2.date)
//            } else {
//                getMonthFromDate(n1.date) - getMonthFromDate(n2.date)
//            }
//        })
//
//        noteListAux.forEach {
//            if (expandableNoteListDetail[convertDate(it)] == null) {
//                expandableNoteListDetail[convertDate(it)] = mutableListOf()
//            }
//            expandableNoteListDetail[convertDate(it)].add(it)
//        }
//    }
//
//    private fun getMonthFromDate(date: Date): Int {
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//        return calendar.get(Calendar.MONTH) + 1
//    }
//
//    private fun getDayFromDate(date: Date): Int {
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//        return calendar.get(Calendar.DAY_OF_MONTH)
//    }
//
//    private fun convertDate(note: Note): String {
//        if (isSameDay(Calendar.getInstance().time, note.date)) {
//            return if (Locale.getDefault().language == "pt") DateUtil.TODAY_PT else DateUtil.TODAY
//        }
//        val noteDate = note.date
//        val noteDateCalendar = Calendar.getInstance()
//        noteDateCalendar.time = noteDate
//
//        val day = noteDateCalendar.get(Calendar.DAY_OF_MONTH)
//        val month = noteDateCalendar.get(Calendar.MONTH)
//        val year = noteDateCalendar.get(Calendar.YEAR)
//
//
//        var date: String
//        if (Locale.getDefault().language == "pt") {
//            date = day.toString() + " de " + MonthUtil.monthsPt[month] + " de " + year
//        } else {
//            date = MonthUtil.months[month] + " " + day.toString() + ", " + year
//        }
//
//        return date
//    }
//
//    private fun isSameDay(dateToday: Date, dateToCompare: Date): Boolean {
//        val calendarDateToday = Calendar.getInstance()
//        val calendarDateToCompare = Calendar.getInstance()
//        calendarDateToday.time = dateToday
//        calendarDateToCompare.time = dateToCompare
//
//        return (calendarDateToday.get(Calendar.DAY_OF_MONTH) == calendarDateToCompare.get(Calendar.DAY_OF_MONTH) &&
//                calendarDateToday.get(Calendar.MONTH) == calendarDateToCompare.get(Calendar.MONTH) &&
//                calendarDateToday.get(Calendar.YEAR) == calendarDateToCompare.get(Calendar.YEAR))
//    }
//
//    object MonthUtil {
//        val monthsPt = arrayOf("Janeiro", "Fevereiro", "Março",
//                "Abril", "Maio", "Junho", "Julho", "Agosto",
//                "Setembro", "Outubro", "Novembro", "Dezembro")
//        val months = arrayOf("January", "February", "March",
//                "April", "May", "June", "July", "August",
//                "September", "October", "November", "December")
//    }
//
//    object DateUtil {
//        const val TODAY_PT = "Hoje"
//        const val TODAY = "Today"
//    }
}