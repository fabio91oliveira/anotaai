package me.fabiooliveira.getnotes.extensions

import android.content.Context
import java.text.DateFormat
import java.text.DateFormat.SHORT
import java.text.SimpleDateFormat
import java.util.*

private const val DAY_NAME_PATTERN = "EEEE"
private const val TIME_PATTERN = "hh:mm a"
private const val TIME_PATTERN_24_HOURS = "HH:mm"

fun Date.getDateString(): String {
    val sdf = DateFormat.getDateInstance(SHORT)
    return sdf.format(this)
}

fun String.getDateFromString(): Date {
    return if (this.isNotEmpty()) {
        val sdf = DateFormat.getDateInstance(SHORT)
        sdf.parse(this)
    } else {
        Date()
    }
}

fun String.getCalendarFromString(): Calendar {
    return if (this.isNotEmpty()) {
        val sdf = DateFormat.getDateInstance(SHORT)
        val date = sdf.parse(this)

        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar
    } else {
        Calendar.getInstance()
    }
}

fun Date.getCalendarFromDate(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Date.getNameOfTheDay(): String {
    val sdf = SimpleDateFormat(DAY_NAME_PATTERN, Locale.getDefault())
    return sdf.format(this)
}

fun Date.getTimeOfTheDay(context: Context): String {
    return if (android.text.format.DateFormat.is24HourFormat(context)) {
        val sdf = SimpleDateFormat(TIME_PATTERN_24_HOURS, Locale.getDefault())
        sdf.format(this)
    } else {
        val sdf = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
        sdf.format(this)
    }
}