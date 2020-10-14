package me.fabiooliveira.getnotes.data.repository

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.domain.model.Note

interface NoteRepository {
    fun publishNote(note: Note): Flow<Long>
    fun remoteNote(id: Long): Flow<Int>
    fun getNotesFromTodayToFuture(): Flow<List<Note>>
    fun getNotesBefore(): Flow<List<Note>>
    fun getNotesByText(name: String): Flow<List<Note>>
    fun getNotesOnlyFromToday(): List<Note>
}