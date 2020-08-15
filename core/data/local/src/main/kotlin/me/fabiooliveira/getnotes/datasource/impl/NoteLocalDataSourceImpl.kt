package me.fabiooliveira.getnotes.datasource.impl

import me.fabiooliveira.getnotes.dao.NoteDao
import me.fabiooliveira.getnotes.datasource.NoteLocalDataSource
import me.fabiooliveira.getnotes.entity.NoteEntity

internal class NoteLocalDataSourceImpl(
        private val noteDao: NoteDao
) : NoteLocalDataSource {
    override fun addNote(noteEntity: NoteEntity) {
        noteDao.insert(noteEntity)
    }

    override fun listNotes(): List<NoteEntity> {
        return noteDao.findAll()
    }
}