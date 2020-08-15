package me.fabiooliveira.getnotes.domain.model

import java.util.*

data class Note(
        val id: Long,
        val title: String,
        val description: String,
        val date: Date,
        val isDone: Boolean
)