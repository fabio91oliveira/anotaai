package me.fabiooliveira.getnotes.presentation.vo

internal data class NoteContent(
        val id: Long,
        val title: String,
        val description: String,
        val isDone: Boolean
)