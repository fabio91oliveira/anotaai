package me.fabiooliveira.getnotes.extensions

import java.text.DateFormat
import java.text.DateFormat.SHORT
import java.text.SimpleDateFormat
import java.util.*

private const val DAY_NAME_PATTERN = "EEEE"

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

fun Date.getNameOfTheDay(): String {
    val sdf = SimpleDateFormat(DAY_NAME_PATTERN, Locale.getDefault())
    return sdf.format(this)
}