package me.fabiooliveira.getnotes.datasource.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.fabiooliveira.getnotes.dao.NoteDao
import me.fabiooliveira.getnotes.datasource.NoteLocalDataSource
import me.fabiooliveira.getnotes.entity.NoteEntity

internal class NoteLocalDataSourceImpl(
        private val noteDao: NoteDao
) : NoteLocalDataSource {
    override fun insertNote(noteEntity: NoteEntity): Flow<Long> {
        return flow {
            emit(noteDao.insert(noteEntity))
        }
    }

    override fun remoteNote(id: Long): Flow<Int> {
        return flow {
            emit(noteDao.delete(id))
        }
    }

    override fun getNotesFromTodayToFuture(): Flow<List<NoteEntity>> {
        return noteDao.findNotesStartingFromToday()
    }

    override fun getNotesBeforeToday(): Flow<List<NoteEntity>> {
        return noteDao.findNotesBeforeToday()
    }

    override fun getNotesByText(text: String): Flow<List<NoteEntity>> {
        return noteDao.findNotesByText(text)
    }

    override fun getNotesOnlyFromToday(): List<NoteEntity> {
        return noteDao.findNotesOnlyFromToday()
    }
}