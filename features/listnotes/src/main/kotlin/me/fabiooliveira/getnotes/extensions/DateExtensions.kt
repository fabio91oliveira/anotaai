package me.fabiooliveira.getnotes.extensions

import java.text.DateFormat.getDateTimeInstance
import java.text.SimpleDateFormat
import java.util.*

private const val DAY_NAME_PATTERN = "EEEE"

fun Date.getDateString(): String {
    val sdf = getDateTimeInstance()
    return sdf.format(this)
}

fun Date.getNameOfTheDay(): String {
    val sdf = SimpleDateFormat(DAY_NAME_PATTERN, Locale.getDefault())
    return sdf.format(this)
}