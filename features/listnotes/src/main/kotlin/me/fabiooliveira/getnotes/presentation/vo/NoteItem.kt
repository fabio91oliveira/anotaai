package me.fabiooliveira.getnotes.presentation.vo

internal data class NoteItem(
        val id: Long,
        val title: String,
        val description: String,
        val dateWithHour: String,
        val dateName: String,
        val relevance: RelevanceEnum,
        val isToday: Boolean
)