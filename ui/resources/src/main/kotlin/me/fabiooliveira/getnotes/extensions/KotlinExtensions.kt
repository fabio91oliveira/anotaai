package me.fabiooliveira.getnotes.extensions

fun <T> T?.whenNull(block: () -> Unit) = this ?: block()