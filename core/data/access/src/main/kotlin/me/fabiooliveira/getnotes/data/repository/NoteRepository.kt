package me.fabiooliveira.getnotes.data.repository

import me.fabiooliveira.getnotes.domain.model.Note

interface NoteRepository {
    suspend fun publishNote(note: Note)
    suspend fun remoteNote(id: Long)
    suspend fun getNotesFromTodayToFuture(): List<Note>
    suspend fun getNotesBefore(): List<Note>
}