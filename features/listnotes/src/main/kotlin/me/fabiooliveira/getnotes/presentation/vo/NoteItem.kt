package me.fabiooliveira.getnotes.presentation.vo

internal data class NoteItem(
        val id: Long,
        val title: String,
        val description: String,
        val isDone: Boolean,
        val date: String
)