package me.fabiooliveira.anotaai.util

import me.fabiooliveira.anotaai.model.entity.Note
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

object DateUtil {
    fun convertDate(note: Note): String {
        if(this.isSameDay(Calendar.getInstance().time, note.date)) {
            return if(Locale.getDefault().language == "pt") DateUtil.TODAY_PT else DateUtil.TODAY
        }
        val noteDate = note.date
        val noteDateCalendar = Calendar.getInstance()
        noteDateCalendar.time = noteDate

        val day = noteDateCalendar.get(Calendar.DAY_OF_MONTH)
        val month = noteDateCalendar.get(Calendar.MONTH)
        val year = noteDateCalendar.get(Calendar.YEAR)


        var date: String
       if(Locale.getDefault().language == "pt") {
           date = day.toString() + " de " + MonthUtil.monthsPt[month] + " de " + year
       } else {
           date = MonthUtil.months[month] + " " + day.toString() + ", " + year
       }

        return date
    }

    fun getMonthFromDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MONTH)+1
    }

    fun getDayFromDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun convertFormatDate(date: String): Date {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(date)
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.time
    }

    fun getDateMinusTodayDate(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -1)
        return cal.time
    }

    fun getCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = Date()


        return calendar
    }

    fun formatDate(dayOfMonth: Int, monthOfYear: Int, year: Int): String{
        val day = if(dayOfMonth.toString().length == 1) "0" + dayOfMonth.toString() else dayOfMonth.toString()
        val month = if(monthOfYear.toString().length == 1) "0" + (monthOfYear+1).toString() else (monthOfYear+1).toString()

        return "$day/$month/$year"
    }

    fun isSameDay(dateToday: Date, dateToCompare: Date): Boolean {
        val calendarDateToday = Calendar.getInstance()
        val calendarDateToCompare = Calendar.getInstance()
        calendarDateToday.time = dateToday
        calendarDateToCompare.time = dateToCompare

        return (calendarDateToday.get(Calendar.DAY_OF_MONTH) == calendarDateToCompare.get(Calendar.DAY_OF_MONTH) &&
                calendarDateToday.get(Calendar.MONTH) == calendarDateToCompare.get(Calendar.MONTH) &&
                calendarDateToday.get(Calendar.YEAR) == calendarDateToCompare.get(Calendar.YEAR))
    }

    object DateUtil {
        const val TODAY_PT = "Hoje"
        const val TODAY = "Today"
    }
}