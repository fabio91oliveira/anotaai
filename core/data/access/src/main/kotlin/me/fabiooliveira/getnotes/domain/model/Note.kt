package me.fabiooliveira.getnotes.domain.model

import java.util.*

data class Note(
        val id: Long = 0,
        val title: String,
        val description: String,
        val date: Date,
        val relevance: Int,
        val isReminder: Boolean
)