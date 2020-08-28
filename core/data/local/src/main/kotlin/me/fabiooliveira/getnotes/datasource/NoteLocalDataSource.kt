package me.fabiooliveira.getnotes.datasource

import me.fabiooliveira.getnotes.entity.NoteEntity

interface NoteLocalDataSource {
    fun insertNote(noteEntity: NoteEntity)
    fun remoteNote(id: Long)
    fun getNotesFromTodayToFuture(): List<NoteEntity>
    fun getNotesBeforeToday(): List<NoteEntity>
}