package me.fabiooliveira.getnotes.datasource.impl

import me.fabiooliveira.getnotes.dao.NoteDao
import me.fabiooliveira.getnotes.datasource.NoteLocalDataSource
import me.fabiooliveira.getnotes.entity.NoteEntity

internal class NoteLocalDataSourceImpl(
        private val noteDao: NoteDao
) : NoteLocalDataSource {
    override fun insertNote(noteEntity: NoteEntity) {
        noteDao.insert(noteEntity)
    }

    override fun remoteNote(id: Long) {
        noteDao.delete(id)
    }

    override fun getNotesFromTodayToFuture(): List<NoteEntity> {
        return noteDao.findNotesStartingFromToday()
    }

    override fun getNotesBeforeToday(): List<NoteEntity> {
        return noteDao.findNotesBeforeToday()
    }
}