package me.fabiooliveira.getnotes.extensions

import java.text.DateFormatSymbols
import java.util.*

fun Date.getMonthNumber(): Int {
    val cal = Calendar.getInstance()
    cal.time = this

    return cal.get(Calendar.MONTH) + 1
}

fun Date.getDayNumber(): Int {
    val cal = Calendar.getInstance()
    cal.time = this

    return cal.get(Calendar.DAY_OF_MONTH)
}

fun Date.getMonthName(): String {
    val month = DateFormatSymbols().months[this.getMonthNumber() - 1]
    val firstString = month.substring(0, 1).toUpperCase()
    val restOfAll = month.substring(1, month.length)

    return firstString + restOfAll
}

fun Date.getCurrentYear() = Calendar.getInstance().let {
    val cal = Calendar.getInstance()
    cal.time = this

    cal.get(Calendar.YEAR).toString()
}