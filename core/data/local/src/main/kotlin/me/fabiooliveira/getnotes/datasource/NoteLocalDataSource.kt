package me.fabiooliveira.getnotes.datasource

import kotlinx.coroutines.flow.Flow
import me.fabiooliveira.getnotes.entity.NoteEntity

interface NoteLocalDataSource {
    fun insertNote(noteEntity: NoteEntity): Flow<Long>
    fun remoteNote(id: Long): Flow<Int>
    fun getNotesFromTodayToFuture(): Flow<List<NoteEntity>>
    fun getNotesBeforeToday(): Flow<List<NoteEntity>>
    fun getNotesByText(name: String): Flow<List<NoteEntity>>
    fun getNotesOnlyFromToday(): List<NoteEntity>
}