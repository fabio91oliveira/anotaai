package me.fabiooliveira.getnotes.datasource

import me.fabiooliveira.getnotes.entity.NoteEntity

interface NoteLocalDataSource {
    fun addNote(noteEntity: NoteEntity)
    fun listNotes(): List<NoteEntity>
}